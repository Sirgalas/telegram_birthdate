package ru.sergalas.admin.client.data.periodicity.update;

import java.util.UUID;

public record UpdateRequestData(
        UUID uuid,
        String chatId,
        String firstName,
        String lastName,
        String patronymic
) {
}
