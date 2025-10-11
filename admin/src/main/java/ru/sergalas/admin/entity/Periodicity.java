package ru.sergalas.admin.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record Periodicity(
        String chatId,
        String title,
        String description,
        String date
) {

}
