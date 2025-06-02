package ru.sergalas.data.entities.participant.exception;

import java.util.NoSuchElementException;

public class ParticipantNotFoundException extends NoSuchElementException {
    public ParticipantNotFoundException(final String message) {
        super(message);
    }
    ParticipantNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
