package ru.sergalas.admin.mapper;

import org.mapstruct.Mapper;
import ru.sergalas.admin.client.data.participant.update.UpdateResponseData;
import ru.sergalas.admin.client.data.periodicity.create.CreateRequestData;
import ru.sergalas.admin.client.data.periodicity.create.CreateResponseData;
import ru.sergalas.admin.client.data.periodicity.update.UpdateRequestData;
import ru.sergalas.admin.client.data.periodicity.view.OnePeriodicityResponseData;
import ru.sergalas.admin.entity.Periodicity;

@Mapper(componentModel = "spring")
public interface PeriodicityMapper {
    Periodicity fromCreateRequestData(CreateResponseData createRequestData);
    Periodicity fromUpdateRequestData(UpdateResponseData updateRequestData);

    Periodicity fromOnePeriodicityData(OnePeriodicityResponseData onePeriodicityResponseData);

    CreateRequestData toCreateRequestData(Periodicity periodicity);
    UpdateRequestData toUpdateRequestData(Periodicity periodicity);
}
