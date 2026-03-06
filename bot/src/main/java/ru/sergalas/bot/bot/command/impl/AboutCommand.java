package ru.sergalas.bot.bot.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sergalas.bot.bot.command.Command;
import ru.sergalas.bot.bot.enums.ButtonTextEnum;
import ru.sergalas.bot.bot.enums.ComandNameEnum;
import ru.sergalas.bot.bot.events.MessageEvent;


@RequiredArgsConstructor
@Component
public class AboutCommand implements Command {

    private final ApplicationEventPublisher publisher;

    @Override
    public boolean canHandle(Update update) {
        if(!update.hasMessage() || !update.getMessage().hasText()) {
            return false;
        }
        Long chatId = update.getMessage().getChatId();
        return update
            .getMessage()
            .getText()
            .equals(
                ButtonTextEnum.ABOUT.getText()
            );
    }

    @Override
    public void handle(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text("Тестовая запись о боте")
                .build();
            publisher.publishEvent(new MessageEvent(this, sendMessage));
        }
    }

    @Override
    public String getCommand() {
        return ComandNameEnum.ABOUT.getName();
    }
}
