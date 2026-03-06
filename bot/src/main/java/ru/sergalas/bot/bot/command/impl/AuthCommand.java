package ru.sergalas.bot.bot.command.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.sergalas.bot.bot.command.Command;
import ru.sergalas.bot.bot.enums.ButtonTextEnum;
import ru.sergalas.bot.bot.enums.ComandNameEnum;
import ru.sergalas.bot.bot.services.KeyboardServices;
import ru.sergalas.bot.bot.util.SendHelper;
import ru.sergalas.bot.services.TelegramBindingService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthCommand implements Command {

    private final TelegramClient telegramClient;
    private final TelegramBindingService service;
    private final SendHelper send;
    private final KeyboardServices keyboardServices;

    @Override
    public boolean canHandle(Update update) {
        if(!update.hasMessage() || !update.getMessage().hasText()) {
            return false;
        }
        return ButtonTextEnum.AUTH.getText().equals(update.getMessage().getText());
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        String username = update.getMessage().getFrom().getUserName();

        service.isUserLinked(chatId)
            .subscribe(
                 linked -> {
                     if(linked) {
                         send.sendMessage(
                             chatId,
                             username,
                             keyboardServices.mainMenu(chatId)
                         );
                     }else {
                         service
                             .generateLinkingCode(chatId, username)
                             .subscribe(
                                 binding -> {
                                     String url = service.buildLinkedUrl(binding.getLinkingCode());
                                     sendAndAuth(chatId,url);
                                 },
                                 e ->  {
                                     log.error("Failed to generate linking code", e);
                                     send.sendMessage(
                                         chatId,
                                         "⚠️ Ошибка при генерации кода",
                                         keyboardServices.mainMenu(chatId)
                                     );
                                 }
                             )
                         ;
                     }
                 },
                    e -> log.error("Error checking auth status", e)
            );

    }

    @Override
    public String getCommand() {
        return ComandNameEnum.AUTH.getName();
    }

    private void sendAndAuth(Long chatId, String uri) {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        rows.add(
                new InlineKeyboardRow(
                        InlineKeyboardButton.builder().text("Войти").build()
                )
        );

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow rowOne = new KeyboardRow();
        rowOne.add("Войти");
        keyboard.add(rowOne);

        String message = """
                🔗 *Привязка аккаунта*
                
                Нажмите на кнопку ниже, чтобы авторизоваться через Keycloak.
                Это позволит вам вносить изменения в данные.
                
                ⏱ Код действителен *15 минут*.
                """;
        send.sendMessage(chatId, message, keyboardServices.getKeyboard(keyboard));
    }
}
