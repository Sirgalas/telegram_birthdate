package ru.sergalas.admin.client.data.participant.update;

public record UpdateRequestData(
        String chatId,
        String firstName,
        String lastName,
        String patronymic,
        String date
) {
}
