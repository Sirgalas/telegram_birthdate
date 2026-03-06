package ru.sergalas.bot.bot.services;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public interface KeyboardServices {
    public ReplyKeyboard mainMenu(Long chatId);
    public ReplyKeyboard getKeyboard(List<KeyboardRow> keyboard);
}
