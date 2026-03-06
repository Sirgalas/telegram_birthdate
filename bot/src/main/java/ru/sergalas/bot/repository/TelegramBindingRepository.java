package ru.sergalas.bot.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.sergalas.bot.entity.TelegramBinding;
import ru.sergalas.bot.bot.enums.BindingStatus;

import java.time.Instant;

@Repository
public interface TelegramBindingRepository extends ReactiveMongoRepository<TelegramBinding, String> {

    Mono<TelegramBinding> findByTelegramChatId(Long telegramChatId);
    Mono<TelegramBinding> findBylinkingCode(String code);
    Mono<TelegramBinding> findByKeycloakSub(String keycloakSub);

    Mono<TelegramBinding> findByTelegramChatIdAndStatusAndCodeExpiresAtAfter(
            Long telegramChatId,
            BindingStatus status,
            Instant codeExpiresAt
    );

    Flux<TelegramBinding> findByStatusAndCodeExpiresAtBefore(
            BindingStatus status,
            Instant codeExpiresAt);
}
