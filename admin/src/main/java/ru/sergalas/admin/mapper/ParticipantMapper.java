package ru.sergalas.admin.mapper;

import org.mapstruct.Mapper;
import ru.sergalas.admin.client.data.participant.create.CreateRequestData;
import ru.sergalas.admin.client.data.participant.update.UpdateRequestData;
import ru.sergalas.admin.client.data.participant.view.OneParticipantData;
import ru.sergalas.admin.entity.Participant;

@Mapper
public interface ParticipantMapper {
    Participant fromCreateRequest(CreateRequestData requestData);
    Participant fromUpdateRequest(UpdateRequestData requestData);

    Participant fromOneParticipantData(OneParticipantData requestData);

    CreateRequestData toCreateRequestData(Participant participant);
    UpdateRequestData toUpdateRequestData(Participant participant);

}
