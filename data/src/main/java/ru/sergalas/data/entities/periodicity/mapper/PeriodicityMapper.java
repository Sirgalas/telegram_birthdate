package ru.sergalas.data.entities.periodicity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.sergalas.data.entities.periodicity.data.PeriodicityRequestData;
import ru.sergalas.data.entities.periodicity.data.PeriodicityResponseData;
import ru.sergalas.data.entities.periodicity.entity.Periodicity;

@Mapper
public interface PeriodicityMapper {
    Periodicity toEntity(PeriodicityRequestData data);
    @Mapping(target = "date", source = "datePeriodicity.date")
    PeriodicityResponseData toDate(Periodicity periodicity);
    void update(@MappingTarget Periodicity entity, PeriodicityRequestData data);
}
