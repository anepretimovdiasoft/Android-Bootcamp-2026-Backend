package ru.sicampus.bootcamp2026.exception.handler;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}