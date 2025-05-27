package ru.sergalas.data.entities.participant.data;

import java.util.UUID;

/**
 * DTO for {@link ru.sergalas.data.entities.participant.entity.Participant}
 */
public record ParticipantResponsePayload(UUID id, String chatId, String firstName, String lastName, String patronymic) {
}