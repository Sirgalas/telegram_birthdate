package ru.sergalas.data.entities.participant.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.sergalas.data.web.data.ResponseData;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults
public class ParticipantResponsePayload implements ResponseData {
    UUID id;
    String chatId;
    String firstName;
    String lastName;
    String patronymic;
}
