package ru.sergalas.bot.consumer.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.sergalas.bot.consumer.data.MessageParticipantRecord;

@RequiredArgsConstructor
@Service
@Slf4j
public class ParticipantConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${app.kafka.participant.topic}", groupId = "${app.kafka.group}")
    public void consume(String message) throws JsonProcessingException {
        MessageParticipantRecord messageRecord = objectMapper.readValue(message, MessageParticipantRecord.class);
        log.debug("messageRecord: {}", messageRecord);
    }
}
