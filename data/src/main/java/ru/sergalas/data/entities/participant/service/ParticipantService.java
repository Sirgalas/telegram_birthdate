package ru.sergalas.data.entities.participant.service;

import ru.sergalas.data.entities.participant.data.ListResponsePayload;
import ru.sergalas.data.entities.participant.data.ParticipantRequestCreatePayload;
import ru.sergalas.data.entities.participant.data.ParticipantRequestUpdatePayload;
import ru.sergalas.data.entities.participant.data.ParticipantResponsePayload;
import ru.sergalas.data.entities.participant.entity.Participant;
import ru.sergalas.data.entities.participant.exception.ParticipantNotFoundException;

import java.util.UUID;

public interface ParticipantService {

    public ParticipantResponsePayload create (ParticipantRequestCreatePayload payload);

    public Participant update (ParticipantRequestUpdatePayload payload, UUID id) throws ParticipantNotFoundException;

    public void delete (String id) throws ParticipantNotFoundException;

    public ListResponsePayload getByDate(String date);
}
