package ru.sergalas.admin.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import ru.sergalas.admin.client.RegisterClient;
import ru.sergalas.admin.client.data.register.create.CreateRequestData;
import ru.sergalas.admin.client.data.register.create.CreateResponseData;
import ru.sergalas.admin.client.data.register.update.UpdateRequestData;
import ru.sergalas.admin.client.data.register.update.UpdateResponseData;

@RequiredArgsConstructor
@Service
public class RegisterClientImpl implements RegisterClient {
    @Value("${birthday.service.user-service-uri}")
    String baseUri;
    RestClient client;

    @Override
    public CreateResponseData create(CreateRequestData createRequestData) {
        return client.post()
            .uri(baseUri + "/register/create")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createRequestData)
            .retrieve()
            .body(CreateResponseData.class);
    }

    @Override
    public UpdateResponseData update(UpdateRequestData updateRequestData, String id) {
        return client
            .post()
            .uri(baseUri + "/register/update/%s".formatted(id))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(updateRequestData)
            .retrieve()
            .body(UpdateResponseData.class);
    }
}
