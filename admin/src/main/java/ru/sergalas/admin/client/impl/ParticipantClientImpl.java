package ru.sergalas.admin.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.sergalas.admin.client.ParticipantClient;
import ru.sergalas.admin.client.data.participant.create.CreateRequestData;
import ru.sergalas.admin.client.data.participant.create.CreateResponseData;
import ru.sergalas.admin.client.data.participant.update.UpdateRequestData;
import ru.sergalas.admin.client.data.participant.view.ListParticipantData;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class ParticipantClientImpl implements ParticipantClient {
    @Value("${birthday.service.data-service-uri}")
    private String baseUri;
    private final RestClient client;

    @Override
    public CreateResponseData createParticipant(CreateRequestData createRequestData) {
        return client
            .post()
            .uri(baseUri + "/participant")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createRequestData)
            .retrieve()
            .body(CreateResponseData.class);
    }

    @Override
    public UpdateRequestData updateParticipant(UpdateRequestData updateRequestData, String id) {
        return client
            .put()
            .uri(baseUri + "/participant/%s".formatted(id))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(updateRequestData)
            .retrieve()
            .body(UpdateRequestData.class);
    }

    @Override
    public void deleteParticipant(String id) {
        client.delete().uri(baseUri + "/participant/%s".formatted(id)).retrieve();
    }

    @Override
    public ListParticipantData getParticipant(String data) {
        URI uri = URI.create(baseUri + "/participant");
        UriBuilder uriBuilder = UriComponentsBuilder.fromUri(uri).queryParam("data", data);
        return client
            .get()
            .uri(uriBuilder.build())
            .retrieve()
            .body(ListParticipantData.class);
    }
}
