package ru.sergalas.admin.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sergalas.admin.client.ParticipantClient;
import ru.sergalas.admin.client.data.participant.create.CreateRequestData;
import ru.sergalas.admin.client.data.participant.update.UpdateRequestData;
import ru.sergalas.admin.client.data.participant.update.UpdateResponseData;
import ru.sergalas.admin.client.data.participant.view.ListParticipantData;
import ru.sergalas.admin.client.data.participant.view.OneParticipantData;
import ru.sergalas.admin.entity.Participant;
import ru.sergalas.admin.mapper.ParticipantMapper;
import ru.sergalas.admin.services.ParticipantService;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantClient participantClient;
    private final ParticipantMapper participantMapper;

    @Override
    public Participant createParticipant(Participant participant) {
        CreateRequestData data = participantMapper.toCreateRequestData(participant);
        return participantMapper.fromCreateRequest(participantClient.createParticipant(data));
    }

    @Override
    public Participant updateParticipant(Participant participant) {
        UpdateRequestData data = participantMapper.toUpdateRequestData(participant);
        return participantMapper.fromUpdateRequest(
            participantClient.updateParticipant(
                data,
                participant.id().toString()
            )
        );
    }

    @Override
    public List<Participant> deleteParticipant(Participant participant) {
        participantClient.deleteParticipant(participant.id().toString());
        return participantClient
            .getParticipant()
            .listParticipant()
            .stream()
            .map(participantMapper::fromOneParticipantData)
            .toList();
    }

    @Override
    public List<Participant> getAllParticipants(String data) {

        return participantClient
            .getParticipant(data)
            .listParticipant()
            .stream()
            .map(participantMapper::fromOneParticipantData)
            .toList();
    }

    @Override
    public OneParticipantData getOneParticipant(Participant participant) {
        return null;
    }
}
