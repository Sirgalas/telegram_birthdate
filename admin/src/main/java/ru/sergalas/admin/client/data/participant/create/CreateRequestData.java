package ru.sergalas.admin.client.data.participant.create;

public record CreateRequestData(
        String chatId,
        String firstName,
        String lastName,
        String patronymic,
        String date
) {
}
