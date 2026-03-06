package ru.sergalas.bot.bot.services;

public interface MessageTrackerService {

    public void saveLastMessage(Long chatId, Integer messageId);
    public void deleteLastMessage(Long chatId);
}
