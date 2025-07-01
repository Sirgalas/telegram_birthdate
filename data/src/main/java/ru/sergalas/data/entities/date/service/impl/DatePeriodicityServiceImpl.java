package ru.sergalas.data.entities.date.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sergalas.data.entities.date.entity.DatePeriodicity;
import ru.sergalas.data.entities.date.repository.DatePeriodicityRepository;
import ru.sergalas.data.entities.date.service.DatePeriodicityService;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class DatePeriodicityServiceImpl implements DatePeriodicityService {

    private final DatePeriodicityRepository dateRepository;

    public  DatePeriodicity getDatePeriodicity(String date) {
        Optional<DatePeriodicity> dateFind = dateRepository.findByDate(date);
        if(dateFind.isPresent()) {
            return dateFind.get();
        } else {
            DatePeriodicity dateNew = new DatePeriodicity();
            dateNew.setDate(date);
            return dateRepository.save(dateNew);
        }
    }
}
