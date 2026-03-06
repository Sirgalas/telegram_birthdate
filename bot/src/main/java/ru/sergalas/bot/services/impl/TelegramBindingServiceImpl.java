package ru.sergalas.bot.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.sergalas.bot.entity.TelegramBinding;
import ru.sergalas.bot.bot.enums.BindingStatus;
import ru.sergalas.bot.bot.exception.LinkedException;
import ru.sergalas.bot.repository.TelegramBindingRepository;
import ru.sergalas.bot.services.TelegramBindingService;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramBindingServiceImpl implements TelegramBindingService {

    private final TelegramBindingRepository repository;

    @Value("${app.telegram.linking.code-duration-minutes}")
    private int codeDurationMinutes;

    @Value("${app.telegram.linking.allowed-chars}")
    private String allowedChars;

    @Value("${app.telegram.linking.web-link-url}")
    private String webLinkTemplate;


    @Override
    public Mono<TelegramBinding> generateLinkingCode(Long chatId, String username) {
        return repository.findByTelegramChatIdAndStatusAndCodeExpiresAtAfter(
                chatId,
                BindingStatus.
                        PENDING,Instant.now()
        ).switchIfEmpty(
            Mono.defer(() ->  repository.save(
                new TelegramBinding(
                    chatId,
                    username,
                    generateCode(),
                    Instant.now().plus(codeDurationMinutes, ChronoUnit.MINUTES)
                )
            ))
        );

    }

    @Override
    public String buildLinkedUrl(String code) {
        return webLinkTemplate.replace("{code}", code);
    }

    @Override
    public Mono<Boolean> isUserLinked(Long chatId) {
        return repository.findByTelegramChatId(chatId)
            .map(build -> build.getStatus().equals(BindingStatus.LINKED))
            .defaultIfEmpty(false);
    }

    @Override
    public Mono<String> getKeycloakSubByChatId(Long chatId) {
        return repository.findByTelegramChatId(chatId)
            .filter(build -> build.getStatus().equals(BindingStatus.LINKED))
            .map(TelegramBinding::getKeycloakSub);
    }

    @Override
    public Mono<Boolean> completeLinking(String code, String keycloakSub) {
        return repository.findBylinkingCode(code)
                .filter(TelegramBinding::canBeLinked)
                .flatMap(binding -> {
                    return repository.findByKeycloakSub(keycloakSub)
                        .hasElement()
                        .flatMap(alreadyLinked -> {
                            if (alreadyLinked) {
                                return Mono.error(new LinkedException(
                                        "Keycloak account already linked to another Telegram"));
                            }
                            binding.setKeycloakSub(keycloakSub);
                            binding.setLinkedAt(Instant.now());
                            changeStatus(binding, BindingStatus.LINKED);
                            return repository.save(binding).thenReturn(true);
                        }
                    );
                })
                .switchIfEmpty(Mono.just(false))
                .onErrorResume(e -> {
                    log.error("Linking failed for code={}", code, e);
                    return Mono.just(false);
                });
    }

    @Override
    public Mono<Integer> cleanupExpiredCodes() {
        Instant now = Instant.now();
        return repository.findByStatusAndCodeExpiresAtBefore(BindingStatus.PENDING, now)
                .flatMap(binding -> repository.save(changeStatus(binding, BindingStatus.REVOKED)))
                .count()
                .map(Long::intValue);
    }

    private TelegramBinding changeStatus(TelegramBinding binding, BindingStatus status) {
        binding.setStatus(status);
        binding.setLinkingCode(null);
        binding.setCodeExpiresAt(null);
        return binding;
    }

    private String generateCode() {
        SecureRandom random = new SecureRandom();
        return random.ints()
                .mapToObj(allowedChars::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

}
