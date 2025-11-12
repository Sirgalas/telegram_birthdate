package ru.sergalas.admin.client;

import ru.sergalas.admin.client.data.participant.create.CreateRequestData;
import ru.sergalas.admin.client.data.participant.create.CreateResponseData;
import ru.sergalas.admin.client.data.participant.update.UpdateRequestData;
import ru.sergalas.admin.client.data.participant.view.ListParticipantData;

public interface ParticipantClient {
    CreateResponseData createParticipant(CreateRequestData createRequestData);
    UpdateRequestData updateParticipant(UpdateRequestData updateRequestData, String id);
    void deleteParticipant(String id);
    ListParticipantData getParticipant(String data);
}
