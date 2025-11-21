package ru.sergalas.admin.client.data.participant.update;

import java.util.UUID;

public record UpdateRequestData(
        UUID id,
        String chatId,
        String firstName,
        String lastName,
        String patronymic,
        String date
) {
}
