package ru.sergalas.bot.bot.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.sergalas.bot.bot.enums.ButtonTextEnum;
import ru.sergalas.bot.bot.services.KeyboardServices;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeyboardServicesImpl implements KeyboardServices {


    @Override
    public ReplyKeyboard mainMenu(Long chatId) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow rowOne = new KeyboardRow();
        rowOne.add(ButtonTextEnum.PARTICIPANT.getText());
        keyboard.add(rowOne);

        KeyboardRow rowTwo = new KeyboardRow();
        rowTwo.add(ButtonTextEnum.ABOUT.getText());
        rowTwo.add(ButtonTextEnum.AUTH.getText());
        keyboard.add(rowTwo);

        KeyboardRow rowThree = new KeyboardRow();
        rowThree.add(ButtonTextEnum.PERIODICITY.getText());
        keyboard.add(rowThree);

        return getKeyboard(keyboard);
    }

    public ReplyKeyboard getKeyboard(List<KeyboardRow> keyboard) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboard);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        return replyKeyboardMarkup;
    }
}
