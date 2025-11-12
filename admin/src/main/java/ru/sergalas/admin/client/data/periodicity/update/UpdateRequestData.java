package ru.sergalas.admin.client.data.periodicity.update;

public record UpdateRequestData(
        String chatId,
        String firstName,
        String lastName,
        String patronymic
) {
}
