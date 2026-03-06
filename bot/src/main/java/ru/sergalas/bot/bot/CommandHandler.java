package ru.sergalas.bot.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sergalas.bot.bot.command.Command;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class CommandHandler {
    private final Collection<Command> commands;

    public void handle(Update update) {
        commands.forEach(command -> {
            if(command.canHandle(update)) {
                command.handle(update);
                return;
            }
        });
    }

}
