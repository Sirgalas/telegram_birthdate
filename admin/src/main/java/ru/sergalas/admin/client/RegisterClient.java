package ru.sergalas.admin.client;

import ru.sergalas.admin.client.data.register.create.CreateRequestData;
import ru.sergalas.admin.client.data.register.create.CreateResponseData;
import ru.sergalas.admin.client.data.register.update.UpdateRequestData;
import ru.sergalas.admin.client.data.register.update.UpdateResponseData;

import java.security.PublicKey;
import java.util.UUID;

public interface RegisterClient {
    public CreateResponseData create(CreateRequestData createRequestData);
    public UpdateResponseData update(UpdateRequestData updateRequestData, String id);
    public void delete(String id);
}
