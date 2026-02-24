package ru.sergalas.data.producer;

public interface Producer {
    public void send(String topic, String message);
}
