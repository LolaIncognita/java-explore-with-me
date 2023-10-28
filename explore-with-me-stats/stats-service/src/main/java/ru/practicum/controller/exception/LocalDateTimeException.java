package ru.practicum.controller.exception;

public class LocalDateTimeException extends RuntimeException {
    public LocalDateTimeException(String message) {
        super(message);
    }
}