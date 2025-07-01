package ru.sergalas.data.entities.periodicity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.sergalas.data.entities.date.entity.DatePeriodicity;
import ru.sergalas.data.entities.date.service.DatePeriodicityService;
import ru.sergalas.data.entities.participant.exception.ParticipantNotFoundException;
import ru.sergalas.data.entities.periodicity.data.PeriodicityRequestData;
import ru.sergalas.data.entities.periodicity.data.PeriodicityResponseData;
import ru.sergalas.data.entities.periodicity.entity.Periodicity;
import ru.sergalas.data.entities.periodicity.mapper.PeriodicityMapper;
import ru.sergalas.data.entities.periodicity.repository.PeriodicityRepository;
import ru.sergalas.data.entities.periodicity.service.impl.PeriodicityServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PeriodicityServiceImplTest {

    @Mock
    private PeriodicityRepository periodicityRepository;
    @Mock
    private DatePeriodicityService datePeriodicityService;
    @Mock
    private PeriodicityMapper periodicityMapper;
    @Mock
    private MessageSource messageSource;

    private String title;
    private String description;
    private String date;

    @BeforeEach
    void setUp() {
        title = "title";
        description = "description";
        date = "25.02";
    }

    @InjectMocks
    PeriodicityServiceImpl periodicityService;

    @Test
    void create_successFull_ReturnsPeriodicityResponseData() throws Exception {
        //given
        var createRequestData = new PeriodicityRequestData(title, description,date);
        var periodicity = setPeriodicity();
        var datePeriodicity = setTestDatePeriodicity();
        var responseData = new PeriodicityResponseData(title,description);
        //when
        when(datePeriodicityService.getDatePeriodicity(any())).thenReturn(datePeriodicity);
        when(periodicityMapper.toEntity(createRequestData)).thenReturn(periodicity);
        when(periodicityRepository.save(periodicity)).thenReturn(periodicity);
        when(periodicityMapper.toDate(periodicity)).thenReturn(responseData);
        PeriodicityResponseData result = periodicityService.create(createRequestData);
        //then
        assertNotNull(result);
        assertEquals(responseData, result);
    }

    @Test
    void update_successFull_ReturnsPeriodicityResponseData() throws Exception {
        //given
        var id = UUID.randomUUID();
        var updateTitle = "updateTitle";
        var updateDescription = "updateDescription";
        var updateDate = "25.02";
        var periodicityRequestData = new PeriodicityRequestData(updateTitle, updateDescription, updateDate);
        var periodicity = setPeriodicity();
        periodicity.setId(id);
        var datePeriodicity = setTestDatePeriodicity();
        var responseData = new PeriodicityResponseData(updateTitle, updateDescription);
        //when
        when(periodicityRepository.findById(id)).thenReturn(Optional.of(periodicity));
        when(datePeriodicityService.getDatePeriodicity(any())).thenReturn(datePeriodicity);
        when(periodicityRepository.save(periodicity)).thenReturn(periodicity);
        when(periodicityMapper.toDate(periodicity)).thenReturn(responseData);
        var result = periodicityService.update(periodicityRequestData,id.toString());
        //then
        assertNotNull(result);
        assertEquals(responseData, result);
    }

    @Test
    void update_failure_PeriodicityNotFound() throws Exception {
        //given
        var id = UUID.randomUUID();
        var updateTitle = "updateTitle";
        var updateDescription = "updateDescription";
        var updateDate = "25.02";
        var periodicityRequestData = new PeriodicityRequestData(updateTitle, updateDescription, updateDate);
        //when
        when(periodicityRepository.findById(id)).thenReturn(Optional.empty());
        //then
        assertThrows(ParticipantNotFoundException.class, () -> periodicityService.update(periodicityRequestData,id.toString()));
    }

    @Test
    void delete_successFull_ReturnEmpty() throws Exception {
        //given
        var id = UUID.randomUUID();
        var periodicity = setPeriodicity();
        periodicity.setId(id);
        //when
        when(periodicityRepository.findById(id)).thenReturn(Optional.of(periodicity));
        doNothing().when(periodicityRepository).delete(periodicity);
        periodicityService.delete(id.toString());
    }

    @Test
    void delete_failure_PeriodicityNotFound() throws Exception {
        //given
        var id = UUID.randomUUID();
        var periodicity = setPeriodicity();
        periodicity.setId(id);
        //when
        when(periodicityRepository.findById(id)).thenReturn(Optional.empty());
        //then
        assertThrows(ParticipantNotFoundException.class, () -> periodicityService.delete(id.toString()));
    }

    @Test
    void getToDate_successFull_returnListPeriodicityData() {
        var periodicity = setPeriodicity();
        periodicity.setId(UUID.randomUUID());
        var periodicityResponseData = new PeriodicityResponseData(title,description);

        when(periodicityRepository.getPeriodicityByDate(date)).thenReturn(List.of(periodicity));
        when(periodicityMapper.toDate(periodicity)).thenReturn(periodicityResponseData);

        var result = periodicityService.getToDate(date);

        assertNotNull(result);
        assertEquals(1, result.getPeriodicities().size());

    }

    private Periodicity setPeriodicity() {
        var periodicity = new Periodicity();
        periodicity.setTitle(title);
        periodicity.setDescription(description);
        return periodicity;
    }

    private DatePeriodicity setTestDatePeriodicity() {
        var datePeriodicity = new DatePeriodicity();
        datePeriodicity.setDate(date);
        return datePeriodicity;
    }
}