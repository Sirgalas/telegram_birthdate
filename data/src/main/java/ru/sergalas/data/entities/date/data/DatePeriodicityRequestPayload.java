package ru.sergalas.data.entities.date.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record DatePeriodicityRequestPayload(
        @Size(min = 5, max = 5, message = "{date_periodicity.size.error}")
        @NotBlank(message = "{date_periodicity.not_blank.error}")
        @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])$", message = "Неверный формат даты. Ожидается формат dd.MM")
        String date) {
}