package ru.sergalas.admin.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record Participant(
        @NotBlank(message = "data.participant.not_blank")
        String chatId,
        @NotBlank(message = "data.participant.not_blank")
        @Size(min = 1, max = 255,message = "{user.firstname.size.error}")
        String firstName,
        @Size(min = 1, max = 255,message = "{user.lastname.size.error}")
        String lastName,
        @Size(min = 1, max = 255,message = "{user.patronymic.size.error}")
        String patronymic,
        @Size(min = 5, max = 5,message = "{date.size.error}")
        @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])$", message = "{date_periodicity.pattern.error}")
        String date
) {
}
