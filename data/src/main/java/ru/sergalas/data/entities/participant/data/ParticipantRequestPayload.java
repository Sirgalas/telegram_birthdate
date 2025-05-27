package ru.sergalas.data.entities.participant.data;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link ru.sergalas.data.entities.participant.entity.Participant}
 */
public record ParticipantRequestPayload(
        @NotBlank
        String chatId,
        @NotBlank
        @Size(min = 1, max = 255,message = "{user.firstname.size.error}")
        String firstName,
        @Size(min = 1, max = 255,message = "{user.lastname.size.error}")
        String lastName,
        @Size(min = 1, max = 255,message = "{user.patronymic.size.error}")
        String patronymic
) {
}