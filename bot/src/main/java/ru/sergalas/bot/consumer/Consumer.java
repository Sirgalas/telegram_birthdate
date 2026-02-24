package ru.sergalas.bot.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Consumer {
    public void consume(String message) throws JsonProcessingException;
}
