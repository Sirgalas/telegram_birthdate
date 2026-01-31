package ru.sergalas.admin.client.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.sergalas.admin.client.ClientHandlerRecord;
import ru.sergalas.admin.client.ParticipantClient;
import ru.sergalas.admin.client.data.participant.create.CreateRequestData;
import ru.sergalas.admin.client.data.participant.create.CreateResponseData;
import ru.sergalas.admin.client.data.participant.update.UpdateRequestData;
import ru.sergalas.admin.client.data.participant.update.UpdateResponseData;
import ru.sergalas.admin.client.data.participant.view.ParticipantsListRecord;
import ru.sergalas.admin.client.helpers.ClientHelper;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipantClientImpl implements ParticipantClient {
    @Value("${birthday.service.data-service-uri}")
    private String baseUri;
    private final RestClient client;

    @Override
    public CreateResponseData createParticipant(CreateRequestData createRequestData) {
        var send =  client
            .post()
            .uri(baseUri + "/participants")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createRequestData);

        return ClientHelper.getReturnedData(
                send,
                new ParameterizedTypeReference<ClientHandlerRecord<CreateResponseData>>(){});
    }

    @Override
    public UpdateResponseData updateParticipant(UpdateRequestData updateRequestData, String id) {
        var send = client
            .put()
            .uri(baseUri + "/participants/%s".formatted(id))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(updateRequestData);

        return ClientHelper.getReturnedData(
                send,
                new ParameterizedTypeReference<ClientHandlerRecord<UpdateResponseData>>(){});
    }

    @Override
    public void deleteParticipant(String id) {
        client
            .delete()
            .uri(baseUri + "/participants/%s".formatted(id))
            .retrieve()
            .toBodilessEntity();
    }

    @Override
    public ParticipantsListRecord getParticipant(String data) {
        URI uri = URI.create(baseUri + "/participants");
        UriBuilder uriBuilder = UriComponentsBuilder.fromUri(uri);
        if(!data.isBlank()){
            uriBuilder.queryParam("date", data);
        }
        var send = client
            .get()
            .uri(uriBuilder.build())
            .retrieve();

        return ClientHelper.getReturnedData(
            send,
            new ParameterizedTypeReference<ClientHandlerRecord<ParticipantsListRecord>>(){}
        );
    }

    @Override
    public ParticipantsListRecord getParticipant() {
        URI uri = URI.create(baseUri + "/participants");
        UriBuilder uriBuilder = UriComponentsBuilder.fromUri(uri);
        var send = client
            .get()
            .uri(uriBuilder.build())
            .retrieve();
        return ClientHelper.getReturnedData(
            send,
            new ParameterizedTypeReference<ClientHandlerRecord<ParticipantsListRecord>>(){}
        );
    }

}
