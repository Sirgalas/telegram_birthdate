package ru.sergalas.data.entities.periodicity.exception;

import java.util.NoSuchElementException;

public class NotFoundPeriodicityException extends NoSuchElementException {
    public NotFoundPeriodicityException(String message){
        super(message);
    }
    public NotFoundPeriodicityException(String message, Throwable cause){
        super(message, cause);
    }
}
