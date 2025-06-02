package ru.sergalas.data.entities.participant.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sergalas.data.entities.date.entity.DatePeriodicity;
import ru.sergalas.data.entities.date.repository.DatePeriodicityRepository;
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

    @Override
    public ParticipantResponsePayload create(ParticipantRequestCreatePayload payload) {
        Participant participant = mapper.toEntity(payload);
        participant.setDatePeriodicity(getDatePeriodicity(payload.date()));
        return mapper.toData(participantRepository.save(participant));
    }

    @Override
    public Participant update(ParticipantRequestUpdatePayload payload) throws ParticipantNotFoundException {

        Participant participant = participantRepository.findById(payload.id()).orElseThrow(() -> new ParticipantNotFoundException("{participant.not_found}"));
        mapper.update(participant, payload);
        participant.setDatePeriodicity(getDatePeriodicity(payload.date()));
        return participantRepository.save(participant);
    }

    @Override
    public void delete(String id) {
        Participant participant = participantRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ParticipantNotFoundException("{participant.not_found}"));
    }

    @Override
    public ParticipantResponsePayload getByDate(String date) {
        return mapper.toData(participantRepository.getParticipantByDate(date).orElse(null));
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
