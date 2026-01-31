package ru.sergalas.data.entities.periodicity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sergalas.data.entities.date.service.DatePeriodicityService;
import ru.sergalas.data.entities.participant.exception.ParticipantNotFoundException;
import ru.sergalas.data.entities.periodicity.data.ListPeriodicityData;
import ru.sergalas.data.entities.periodicity.data.PeriodicityRequestData;
import ru.sergalas.data.entities.periodicity.data.PeriodicityResponseData;
import ru.sergalas.data.entities.periodicity.entity.Periodicity;
import ru.sergalas.data.entities.periodicity.mapper.PeriodicityMapper;
import ru.sergalas.data.entities.periodicity.repository.PeriodicityRepository;
import ru.sergalas.data.entities.periodicity.service.PeriodicityService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PeriodicityServiceImpl implements PeriodicityService {
    private final PeriodicityRepository periodicityRepository;
    private final DatePeriodicityService datePeriodicityService;
    private final PeriodicityMapper periodicityMapper;

    @Override
    public PeriodicityResponseData create(PeriodicityRequestData data) {
        Periodicity periodicity = periodicityMapper.toEntity(data);
        periodicity.setDatePeriodicity(datePeriodicityService.getDatePeriodicity(data.getDate()));
        return periodicityMapper.toDate(periodicityRepository.save(periodicity));
    }

    @Override
    public PeriodicityResponseData update(PeriodicityRequestData data, String id) {
        Periodicity periodicity = periodicityRepository
            .findById(UUID.fromString(id))
            .orElseThrow(() -> new ParticipantNotFoundException("{periodicity.error.not_found}"));
        periodicityMapper.update(periodicity, data);
        periodicity.setDatePeriodicity(datePeriodicityService.getDatePeriodicity(data.getDate()));
        return periodicityMapper.toDate(periodicityRepository.save(periodicity));
    }

    @Override
    public void delete(String id) {
        Periodicity periodicity = periodicityRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new ParticipantNotFoundException("{periodicity.error.not_found}"));
        periodicityRepository.delete(periodicity);
    }

    @Override
    public ListPeriodicityData getToDate(String date) {
        if(date != null) {
            return new ListPeriodicityData(
                periodicityRepository.getPeriodicityByDate(date).stream().map(periodicityMapper::toDate).toList()
            );
        }
        return new ListPeriodicityData(
            periodicityRepository.findAll().stream().map(periodicityMapper::toDate).toList()
        );
    }

    @Override
    public PeriodicityResponseData getPeriodicity(String id) {
        return  periodicityRepository
                .findById(UUID.fromString(id))
                .map(periodicityMapper::toDate)
                .orElseThrow(() -> new ParticipantNotFoundException("{periodicity.error.not_found}") );

    }
}
