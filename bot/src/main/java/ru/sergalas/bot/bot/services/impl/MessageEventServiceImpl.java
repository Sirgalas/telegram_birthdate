package ru.sergalas.bot.bot.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.sergalas.bot.bot.events.MessageEvent;
import ru.sergalas.bot.bot.services.KeyboardServices;
import ru.sergalas.bot.bot.services.MessageEventService;

@RequiredArgsConstructor
@Service
public class MessageEventServiceImpl implements MessageEventService {

    private final ApplicationEventPublisher publisher;
    private final KeyboardServices keyboardServices;

    @Override
    public void send(Long chatId, String message, Object object) {
        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .replyMarkup(keyboardServices.mainMenu(chatId))
                .text(message)
                .build();

        publisher.publishEvent(new MessageEvent(object,sendMessage));
    }
}
