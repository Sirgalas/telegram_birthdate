package ru.sergalas.data.producer.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.sergalas.data.producer.Producer;


@RequiredArgsConstructor
@Service
public class KafkaProducer implements Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
