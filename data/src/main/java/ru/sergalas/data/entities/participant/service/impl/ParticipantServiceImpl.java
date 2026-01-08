package ru.sergalas.data.entities.participant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sergalas.data.entities.date.entity.DatePeriodicity;
import ru.sergalas.data.entities.date.repository.DatePeriodicityRepository;
import ru.sergalas.data.entities.date.service.DatePeriodicityService;
import ru.sergalas.data.entities.participant.data.ListResponsePayload;
import ru.sergalas.data.entities.participant.data.ParticipantRequestCreatePayload;
import ru.sergalas.data.entities.participant.data.ParticipantRequestUpdatePayload;
import ru.sergalas.data.entities.participant.data.ParticipantResponsePayload;
import ru.sergalas.data.entities.participant.entity.Participant;
import ru.sergalas.data.entities.participant.exception.ParticipantNotFoundException;
import ru.sergalas.data.entities.participant.mapper.ParticipantMapper;
import ru.sergalas.data.entities.participant.repository.ParticipantRepository;
import ru.sergalas.data.entities.participant.service.ParticipantService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantRepository participantRepository;
    private final DatePeriodicityRepository dateRepository;
    private final ParticipantMapper mapper;
    private final DatePeriodicityService datePeriodicityService;

    @Override
    public ParticipantResponsePayload create(ParticipantRequestCreatePayload payload) {
        Participant participant = mapper.toEntity(payload);
        participant.setDatePeriodicity(datePeriodicityService.getDatePeriodicity(payload.date()));
        return mapper.toData(participantRepository.save(participant));
    }

    @Override
    public ParticipantResponsePayload update(ParticipantRequestUpdatePayload payload, UUID id) throws ParticipantNotFoundException {

        Participant participant = participantRepository.findById(id).orElseThrow(() -> new ParticipantNotFoundException("{participant.not_found}"));
        mapper.update(participant, payload);
        participant.setDatePeriodicity(datePeriodicityService.getDatePeriodicity(payload.date()));
        return mapper.toData(participantRepository.save(participant));
    }

    @Override
    public void delete(String id) throws ParticipantNotFoundException {
        Participant participant = participantRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ParticipantNotFoundException("{participant.not_found}"));
        participantRepository.delete(participant);
    }

    @Override
    public ListResponsePayload getByDate(String date) {
        if(date != null) {
            return new ListResponsePayload(
                participantRepository.getParticipantByDate(date).stream().map(mapper::toData).toList()
            );
        }
        return new ListResponsePayload(
            participantRepository.findAll().stream().map(mapper::toData).toList()
        );
    }

    @Override
    public ParticipantResponsePayload getById(String id) throws ParticipantNotFoundException {
        return participantRepository.findById(UUID.fromString(id)).map(mapper::toData).orElseThrow(() -> new ParticipantNotFoundException("{participant.not_found}"));
    }

    private DatePeriodicity getDatePeriodicity(String date) {
        Optional<DatePeriodicity> dateFind = dateRepository.findByDate(date);
        if(dateFind.isPresent()) {
            return dateFind.get();
        } else {
            DatePeriodicity dateNew = new DatePeriodicity();
            dateNew.setDate(date);
            return dateNew;
        }
    }
}
