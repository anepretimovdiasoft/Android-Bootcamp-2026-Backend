package ru.sicampus.bootcamp2026.exception.handler;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}