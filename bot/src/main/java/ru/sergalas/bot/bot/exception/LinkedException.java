package ru.sergalas.bot.bot.exception;

public class LinkedException extends RuntimeException {
    public LinkedException(String message) {
        super(message);
    }
    public LinkedException(String message, Throwable cause) {
        super(message, cause);
    }
    public LinkedException(Throwable cause) {
        super(cause);
    }
}
