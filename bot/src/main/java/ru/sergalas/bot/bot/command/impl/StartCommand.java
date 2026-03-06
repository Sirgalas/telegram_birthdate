package ru.sergalas.bot.bot.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sergalas.bot.bot.command.Command;
import ru.sergalas.bot.bot.enums.ButtonTextEnum;
import ru.sergalas.bot.bot.enums.ComandNameEnum;
import ru.sergalas.bot.bot.services.KeyboardServices;
import ru.sergalas.bot.bot.util.SendHelper;

@RequiredArgsConstructor
@Component
public class StartCommand implements Command {

    private final SendHelper send;
    private final KeyboardServices keyboardServices;

    @Override
    public boolean canHandle(Update update) {
        if(!update.hasMessage() && !update.getMessage().hasText()) {
            return false;
        }
        return ButtonTextEnum.START.getText().equals(update.getMessage().getText());
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        String username = update.getMessage().getFrom().getUserName();
        String message = "👋 Привет, @" + username + "!\n\n" +
                "Выберите действие из меню:";
        send.sendMessage(
                chatId,
                message,
                keyboardServices.mainMenu(chatId)
        );
    }

    @Override
    public String getCommand() {
        return ComandNameEnum.START.toString();
    }
}


