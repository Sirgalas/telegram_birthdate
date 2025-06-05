package ru.sergalas.data.entities.participant.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

public record ParticipantRequestUpdatePayload(
        @NotBlank
        String chatId,
        @NotBlank
        @Size(min = 1, max = 255,message = "{user.firstname.size.error}")
        String firstName,
        @Size(min = 1, max = 255,message = "{user.lastname.size.error}")
        String lastName,
        @Size(min = 1, max = 255,message = "{user.patronymic.size.error}")
        String patronymic,
        @NotBlank
        @Size(min = 5, max = 5,message = "{date.size.error}")
        @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])$")
        String date
) {
}
