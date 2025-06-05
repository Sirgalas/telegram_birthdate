package ru.sergalas.data.entities.participant.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.sergalas.data.web.data.ResponseData;
import ru.sergalas.data.web.payload.ResponsePayload;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListResponsePayload implements ResponseData {
    List<ParticipantResponsePayload> participants;
}
