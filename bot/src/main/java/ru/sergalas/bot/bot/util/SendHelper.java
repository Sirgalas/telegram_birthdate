package ru.sergalas.bot.bot.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.sergalas.bot.bot.services.KeyboardServices;

@Slf4j
@RequiredArgsConstructor
@Component
public class SendHelper {

    private final TelegramClient telegramClient;

    public  void sendMessage(Long chatId, String text, ReplyKeyboard reply) {

        try{
            SendMessage.SendMessageBuilder<?, ?> builder = SendMessage.builder().chatId(chatId).text(text);
            if(reply != null) {
                builder.replyMarkup(reply);
            }
            telegramClient.execute(builder.build());
        } catch (TelegramApiException e) {
            log.error("error {}",e.getMessage());
        }
    }
}
