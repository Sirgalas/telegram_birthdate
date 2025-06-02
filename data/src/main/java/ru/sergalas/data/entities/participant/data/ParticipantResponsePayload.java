package ru.sergalas.data.entities.participant.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.sergalas.data.web.data.ResponseData;

import java.util.UUID;

public class ParticipantResponsePayload implements ResponseData {
    UUID id;
    String chatId;
    String firstName;
    String lastName;
    String patronymic;
}
