package ru.practicum.controller.exception;

import lombok.Getter;

@Getter
class ErrorResponse {
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}