package ru.sergalas.admin.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ClientHandlerRecord<T>(
       Integer code,
       T data,
       String error
) {
}
