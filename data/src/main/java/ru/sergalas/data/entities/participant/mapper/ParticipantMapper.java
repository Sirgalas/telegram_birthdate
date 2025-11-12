package ru.sergalas.data.entities.participant.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import ru.sergalas.data.entities.participant.data.ParticipantRequestCreatePayload;
import ru.sergalas.data.entities.participant.data.ParticipantRequestUpdatePayload;
import ru.sergalas.data.entities.participant.data.ParticipantResponsePayload;
import ru.sergalas.data.entities.participant.entity.Participant;

@Mapper
public interface ParticipantMapper {
    Participant toEntity(ParticipantRequestCreatePayload payload);

    @Mapping(target = "date", source = "datePeriodicity.date")
    ParticipantResponsePayload toData(Participant entity);

    void update(@MappingTarget Participant entity, ParticipantRequestUpdatePayload payload);
}
