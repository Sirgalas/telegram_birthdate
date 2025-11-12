package ru.sergalas.admin.client;

import ru.sergalas.admin.client.data.participant.update.UpdateResponseData;
import ru.sergalas.admin.client.data.periodicity.create.CreateRequestData;
import ru.sergalas.admin.client.data.periodicity.create.CreateResponseData;
import ru.sergalas.admin.client.data.periodicity.update.UpdateRequestData;
import ru.sergalas.admin.client.data.periodicity.view.ListPeriodicityData;

public interface PeriodicityClient {
    public CreateResponseData createPeriodicity(CreateRequestData createRequestData);
    public UpdateResponseData updatePeriodicity(UpdateRequestData updateRequestData, String id);
    public void deletePeriodicity(String id);
    public ListPeriodicityData getPeriodicity(String data);
}
