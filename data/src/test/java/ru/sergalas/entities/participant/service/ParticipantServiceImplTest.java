package ru.sergalas.entities.participant.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.sergalas.data.entities.date.entity.DatePeriodicity;
import ru.sergalas.data.entities.date.repository.DatePeriodicityRepository;
import ru.sergalas.data.entities.date.service.DatePeriodicityService;
import ru.sergalas.data.entities.participant.data.ParticipantRequestCreatePayload;
import ru.sergalas.data.entities.participant.data.ParticipantRequestUpdatePayload;
import ru.sergalas.data.entities.participant.data.ParticipantResponsePayload;
import ru.sergalas.data.entities.participant.entity.Participant;
import ru.sergalas.data.entities.participant.exception.ParticipantNotFoundException;
import ru.sergalas.data.entities.participant.mapper.ParticipantMapper;
import ru.sergalas.data.entities.participant.repository.ParticipantRepository;
import ru.sergalas.data.entities.participant.service.impl.ParticipantServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceImplTest {


    @Mock
    ParticipantRepository participantRepository;

    @Mock
    DatePeriodicityRepository dateRepository;

    @Mock
    ParticipantMapper mapper;

    @Mock
    DatePeriodicityService datePeriodicityService;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private ParticipantServiceImpl participantService;

    private String chatId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String date;

    @BeforeEach
    void setUp() {
        chatId = UUID.randomUUID().toString();
        firstName = "John";
        lastName = "Doe";
        patronymic = "Patronymic";
        date = "29.11";
    }

    @Test
    void create_SaveParticipant_Return_ParticipantResponsePayload() throws Exception {
        //given
        var payload = new ParticipantRequestCreatePayload(chatId,firstName,lastName,patronymic,date);
        var participant = setTestParticipant();
        var datePeriodicity = setTestDatePeriodicity();

        var responsePayload = new ParticipantResponsePayload(UUID.randomUUID(),chatId,firstName,lastName,patronymic);
        //when
        when(datePeriodicityService.getDatePeriodicity(any())).thenReturn(datePeriodicity);
        when(mapper.toEntity(payload)).thenReturn(participant);
        when(participantRepository.save(participant)).thenReturn(participant);
        when(mapper.toData(participant)).thenReturn(responsePayload);
        ParticipantResponsePayload returnPayload = participantService.create(payload);
        //then

        assertNotNull(returnPayload);
        assertEquals(responsePayload, returnPayload);

    }

    @Test
    void update_successFull_Return_ParticipantResponsePayload() throws Exception {
        //given
        var id = UUID.randomUUID();
        String newFirstName = "Bill";
        String newLastName = "Smith";
        String newPatronymic = "First";
        String newDate = "29.11";
        var participantRequestPayload =  new ParticipantRequestUpdatePayload(chatId,newFirstName,newLastName,newPatronymic,newDate);
        var participant = setTestParticipant();
        participant.setId(id);
        DatePeriodicity datePeriodicity = setTestDatePeriodicity();
        var participantResponsePayload = new ParticipantResponsePayload(id,newFirstName,newLastName,newPatronymic,newDate);

        //when
        when(participantRepository.findById(id)).thenReturn(Optional.of(participant));
        when(datePeriodicityService.getDatePeriodicity(any())).thenReturn(datePeriodicity);
        when(participantRepository.save(participant)).thenReturn(participant);
        when(mapper.toData(participant)).thenReturn(participantResponsePayload);

        var resultParticipantResponsePayload = participantService.update(participantRequestPayload,id);

        //then
        assertNotNull(resultParticipantResponsePayload);
        assertEquals(participantResponsePayload, resultParticipantResponsePayload);

    }
    @Test
    public void update_failure_ParticipantNotFound() throws Exception  {
        //given
        var id = UUID.randomUUID();
        String newFirstName = "Bill";
        String newLastName = "Smith";
        String newPatronymic = "First";
        String newDate = "29.11";
        var participantRequestPayload =  new ParticipantRequestUpdatePayload(chatId,newFirstName,newLastName,newPatronymic,newDate);

        //when
        when(participantRepository.findById(id)).thenReturn(Optional.empty());

        //given
        assertThrows(ParticipantNotFoundException.class, () -> participantService.update(participantRequestPayload,id));
    }


    @Test
    void delete_successFull_ReturnEmpty() throws Exception {
        //given
        var id = UUID.randomUUID();
        var participant = setTestParticipant();
        participant.setId(id);
        //when
        when(participantRepository.findById(id)).thenReturn(Optional.of(participant));
        doNothing().when(participantRepository).delete(participant);
        participantService.delete(id.toString());
    }

    @Test
    void delete_failure_ReturnException() throws Exception {
        //given
        var id = UUID.randomUUID();
        var participant = setTestParticipant();
        //when
        when(participantRepository.findById(id)).thenReturn(Optional.empty());
        //then
        assertThrows(ParticipantNotFoundException.class, () -> participantService.delete(id.toString()));
    }

    @Test
    void getByDate_successFull_ListResponsePayload() throws Exception {
        var participant = setTestParticipant();
        participant.setId(UUID.randomUUID());
        var responsePayload = new ParticipantResponsePayload(UUID.randomUUID(),chatId,firstName,lastName,patronymic);

        when(participantRepository.getParticipantByDate(date)).thenReturn(List.of(participant));
        when(mapper.toData(participant)).thenReturn(responsePayload);

        var resultListParticipant = participantService.getByDate(date);
        assertNotNull(resultListParticipant);
        assertEquals(1, resultListParticipant.getParticipants().size());
    }



    private Participant setTestParticipant() {
        var participant = new Participant();
        participant.setChatId(chatId);
        participant.setFirstName(firstName);
        participant.setLastName(lastName);
        participant.setPatronymic(patronymic);
        return participant;
    }

    private DatePeriodicity setTestDatePeriodicity() {
        var datePeriodicity = new DatePeriodicity();
        datePeriodicity.setDate(date);
        return datePeriodicity;
    }


}