package ru.sergalas.admin.mapper;

import org.mapstruct.Mapper;
import ru.sergalas.admin.client.data.periodicity.create.CreateRequestData;
import ru.sergalas.admin.client.data.periodicity.update.UpdateRequestData;
import ru.sergalas.admin.client.data.periodicity.view.OnePeriodicityResponseData;
import ru.sergalas.admin.entity.Periodicity;

@Mapper
public interface PeriodicityMapper {
    Periodicity fromCreateRequestData(CreateRequestData createRequestData);
    Periodicity toUpdateRequestData(UpdateRequestData updateRequestData);

    Periodicity fromOnePeriodicityData(OnePeriodicityResponseData onePeriodicityResponseData);

    CreateRequestData toCreateRequestData(Periodicity periodicity);
    UpdateRequestData toUpdateRequestData(Periodicity periodicity);
}
