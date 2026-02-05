package ru.sicampus.bootcamp2026.exception.position;

public class PositionNotFoundException extends RuntimeException {
    public PositionNotFoundException(String message) {
        super(message);
    }
}
