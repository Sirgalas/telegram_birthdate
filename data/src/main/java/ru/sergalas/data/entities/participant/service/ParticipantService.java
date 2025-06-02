package ru.sergalas.data.entities.participant.service;

import ru.sergalas.data.entities.participant.data.ParticipantRequestCreatePayload;
import ru.sergalas.data.entities.participant.data.ParticipantRequestUpdatePayload;
import ru.sergalas.data.entities.participant.data.ParticipantResponsePayload;
import ru.sergalas.data.entities.participant.entity.Participant;

public interface ParticipantService {

    public ParticipantResponsePayload create (ParticipantRequestCreatePayload payload);

    public Participant update (ParticipantRequestUpdatePayload payload);

    public void delete (String id);

    public ParticipantResponsePayload getByDate(String date);
}
