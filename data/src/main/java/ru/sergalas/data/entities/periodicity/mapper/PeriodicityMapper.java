package ru.sergalas.data.entities.periodicity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.sergalas.data.entities.periodicity.data.PeriodicityCreateRequestData;
import ru.sergalas.data.entities.periodicity.data.PeriodicityResponseData;
import ru.sergalas.data.entities.periodicity.entity.Periodicity;

@Mapper
public interface PeriodicityMapper {
    Periodicity toEntity(PeriodicityCreateRequestData data);
    PeriodicityResponseData toDate(Periodicity periodicity);
    void update(@MappingTarget Periodicity entity, PeriodicityCreateRequestData data);
}
