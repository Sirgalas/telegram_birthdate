package ru.sergalas.admin.client.data.periodicity.create;

public record CreateRequestData(
        String chatId,
        String title,
        String description,
        String date
) {
}
