package ru.sergalas.admin.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import ru.sergalas.admin.client.ClientHandlerRecord;
import ru.sergalas.admin.client.RegisterClient;
import ru.sergalas.admin.client.data.periodicity.view.ListPeriodicityData;
import ru.sergalas.admin.client.data.register.create.CreateRequestData;
import ru.sergalas.admin.client.data.register.create.CreateResponseData;
import ru.sergalas.admin.client.data.register.update.UpdateRequestData;
import ru.sergalas.admin.client.data.register.update.UpdateResponseData;
import ru.sergalas.admin.client.helpers.ClientHelper;

@RequiredArgsConstructor
@Service
public class RegisterClientImpl implements RegisterClient {
    @Value("${birthday.service.user-service-uri}")
    String baseUri;
    private final RestClient client;

    @Override
    public CreateResponseData create(CreateRequestData createRequestData) {
        var send = client.post()
            .uri(baseUri + "/registry")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createRequestData);
        return ClientHelper.getReturnedData(
                send,
                new ParameterizedTypeReference<ClientHandlerRecord<CreateResponseData>>(){}
        );

    }

    @Override
    public UpdateResponseData update(UpdateRequestData updateRequestData, String id) {
        var send = client
            .put()
            .uri(baseUri + "/registry/%s".formatted(id))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(updateRequestData);
        return ClientHelper.getReturnedData(
                send,
                new ParameterizedTypeReference<ClientHandlerRecord<UpdateResponseData>>(){}
        );
    }

    @Override
    public void delete(String id) {
        client.delete().uri(baseUri + "/registry/%s".formatted(id)).retrieve().toBodilessEntity();;
    }
}
