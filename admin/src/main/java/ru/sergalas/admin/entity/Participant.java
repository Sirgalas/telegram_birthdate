package ru.sergalas.admin.entity;

public record Participant(
        String chatId,
        String firstName,
        String lastname,
        String patronymic,
        String date
) {
}
