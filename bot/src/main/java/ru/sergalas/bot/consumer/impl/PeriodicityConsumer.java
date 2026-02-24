package ru.sergalas.bot.consumer.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.sergalas.bot.consumer.Consumer;
import ru.sergalas.bot.consumer.data.MessageParticipantRecord;
import ru.sergalas.bot.consumer.data.MessagePeriodicityRecord;

@RequiredArgsConstructor
@Service
@Slf4j
public class PeriodicityConsumer implements Consumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${app.kafka.periodicity.topic}", groupId = "${app.kafka.group}")
    @Override
    public void consume(String message) throws JsonProcessingException {
        MessagePeriodicityRecord messageRecord = objectMapper.readValue(message, MessagePeriodicityRecord.class);
        log.debug("messageRecord: {}", messageRecord);
    }
}
