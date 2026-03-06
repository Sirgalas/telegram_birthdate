package ru.sergalas.data.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.sergalas.data.command.data.MessageParticipantRecord;
import ru.sergalas.data.command.data.MessagePeriodicityRecord;
import ru.sergalas.data.entities.participant.service.ParticipantService;
import ru.sergalas.data.entities.periodicity.service.PeriodicityService;
import ru.sergalas.data.producer.Producer;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class SenderScheduler {
    private final Producer producer;
    private final ParticipantService participantService;
    private final PeriodicityService periodicityService;
    private final ObjectMapper objectMapper;

    @Value("${app.kafka.participant_topic}")
    private String participantTopic;

    @Value("${app.kafka.periodicity_topic}")
    private String periodicityTopic;

    @Scheduled(cron = "${app.cronParticipant}")
    public void sendBirtdateParticipant ()
    {
        LocalDate localDate = LocalDate.now();
        participantService.getByLocalDate(localDate).forEach(participant -> {
            String fullName = participant.getFullName();
            try {
                producer.send(
                    participantTopic,
                    objectMapper.writeValueAsString(
                        new MessageParticipantRecord(
                            "Поздравляем %s".formatted(fullName),
                            participant.getChatId()
                        )
                    )
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Scheduled(cron = "${app.cronPeriodicity}")
    public void sendPeriodicity()
    {
        LocalDate localDate = LocalDate.now();
        periodicityService.getByDate(localDate).forEach(periodicity -> {
            try {
                producer.send(
                    periodicityTopic,
                    objectMapper.writeValueAsString(
                        new MessagePeriodicityRecord(
                            periodicity.getDescription(),
                            periodicity.getTitle(),
                            periodicity.getChatId()
                        )
                    )
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
