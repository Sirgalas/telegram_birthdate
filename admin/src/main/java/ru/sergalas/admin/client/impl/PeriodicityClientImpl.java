package ru.sergalas.admin.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.sergalas.admin.client.ClientHandlerRecord;
import ru.sergalas.admin.client.PeriodicityClient;
import ru.sergalas.admin.client.data.participant.update.UpdateResponseData;
import ru.sergalas.admin.client.data.participant.view.ParticipantsListRecord;
import ru.sergalas.admin.client.data.periodicity.create.CreateRequestData;
import ru.sergalas.admin.client.data.periodicity.create.CreateResponseData;
import ru.sergalas.admin.client.data.periodicity.update.UpdateRequestData;
import ru.sergalas.admin.client.data.periodicity.view.ListPeriodicityData;
import ru.sergalas.admin.client.helpers.ClientHelper;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class PeriodicityClientImpl implements PeriodicityClient {
    @Value("${birthday.service.data-service-uri}")
    String baseUri;

    private final RestClient client;

    @Override
    public CreateResponseData createPeriodicity(CreateRequestData createRequestData) {
        var send = client
            .post()
            .uri(baseUri + "/periodicity")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createRequestData);

        return ClientHelper.getReturnedData(
            send,
            new ParameterizedTypeReference<ClientHandlerRecord<CreateResponseData>>(){});

    }

    @Override
    public UpdateResponseData updatePeriodicity(UpdateRequestData updateRequestData, String id) {
        var send = client
            .put()
            .uri(baseUri + "/periodicity/%s".formatted(id))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(updateRequestData);
        return ClientHelper.getReturnedData(
                send,
                new ParameterizedTypeReference<ClientHandlerRecord<UpdateResponseData>>(){});

    }

    @Override
    public void deletePeriodicity(String id) {
        client
            .delete()
            .uri(baseUri + "/periodicity/%s".formatted(id))
            .retrieve()
            .toBodilessEntity();;
    }

    @Override
    public ListPeriodicityData getPeriodicity(String data) {
        URI uri = URI.create(baseUri + "/periodicity");
        UriBuilder uriBuilder = UriComponentsBuilder.fromUri(uri);
        if(!data.isBlank()) {
            uriBuilder.queryParam("data", data);
        }

        var send = client
            .get()
            .uri(uriBuilder.build())
            .retrieve();

        return ClientHelper.getReturnedData(
                send,
                new ParameterizedTypeReference<ClientHandlerRecord<ListPeriodicityData>>(){}
        );
    }

    @Override
    public ListPeriodicityData getPeriodicity() {
        URI uri = URI.create(baseUri + "/periodicity");
        UriBuilder uriBuilder = UriComponentsBuilder.fromUri(uri);
        return client
                .get()
                .uri(uriBuilder.build())
                .retrieve()
                .body(ListPeriodicityData.class);
    }
}
