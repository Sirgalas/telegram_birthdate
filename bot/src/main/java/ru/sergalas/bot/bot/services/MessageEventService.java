package ru.sergalas.bot.bot.services;

public interface MessageEventService {
    void send(Long chatId, String message, Object object);
}
