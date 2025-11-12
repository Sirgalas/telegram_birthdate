package ru.sergalas.admin.client;

import ru.sergalas.admin.client.data.register.create.CreateRequestData;
import ru.sergalas.admin.client.data.register.create.CreateResponseData;
import ru.sergalas.admin.client.data.register.update.UpdateRequestData;
import ru.sergalas.admin.client.data.register.update.UpdateResponseData;

public interface RegisterClient {
    public CreateResponseData create(CreateRequestData createRequestData);
    public UpdateResponseData update(UpdateRequestData updateRequestData, String id);
}
