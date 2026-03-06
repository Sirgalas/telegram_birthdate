package ru.sergalas.bot.services;

import reactor.core.publisher.Mono;
import ru.sergalas.bot.entity.TelegramBinding;

public interface TelegramBindingService {
    public Mono<TelegramBinding> generateLinkingCode(Long chatId, String username);
    public String buildLinkedUrl(String code);
    public Mono<Boolean> isUserLinked(Long chatId);
    public Mono<String> getKeycloakSubByChatId(Long chatId);
    public Mono<Boolean> completeLinking(String code, String keycloakSub);
    public Mono<Integer> cleanupExpiredCodes();
}
