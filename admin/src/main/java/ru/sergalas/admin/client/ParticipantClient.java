package ru.sergalas.admin.client;

import ru.sergalas.admin.client.data.participant.create.CreateRequestData;
import ru.sergalas.admin.client.data.participant.create.CreateResponseData;
import ru.sergalas.admin.client.data.participant.update.UpdateRequestData;
import ru.sergalas.admin.client.data.participant.update.UpdateResponseData;
import ru.sergalas.admin.client.data.participant.view.ListParticipantData;
import ru.sergalas.admin.client.data.participant.view.OneParticipantData;

public interface ParticipantClient {
    CreateResponseData createParticipant(CreateRequestData createRequestData);
    UpdateResponseData updateParticipant(UpdateRequestData updateRequestData, String id);
    void deleteParticipant(String id);
    ListParticipantData getParticipant(String data);
    ListParticipantData getParticipant();
    OneParticipantData getOneParticipant(String id);
}
