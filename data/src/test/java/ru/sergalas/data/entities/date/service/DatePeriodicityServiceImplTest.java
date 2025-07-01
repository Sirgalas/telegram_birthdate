package ru.sergalas.data.entities.date.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sergalas.data.entities.date.entity.DatePeriodicity;
import ru.sergalas.data.entities.date.repository.DatePeriodicityRepository;
import ru.sergalas.data.entities.date.service.impl.DatePeriodicityServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DatePeriodicityServiceImplTest {

    @Mock
    private DatePeriodicityRepository datePeriodicityRepository;

    @InjectMocks
    private DatePeriodicityServiceImpl datePeriodicityService;

    private String date;

    @BeforeEach
    void setUp() {
        date = "09.09";
    }

    @Test
    void getDatePeriodicity_successFull() {
        var datePeriodicity = new DatePeriodicity();
        datePeriodicity.setDate(date);

        when(datePeriodicityRepository.findByDate(date)).thenReturn(Optional.of(datePeriodicity));
        var result = datePeriodicityService.getDatePeriodicity(date);

        assertNotNull(result);
        assertEquals(date, result.getDate());
    }
}