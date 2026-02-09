package ru.sicampus.bootcamp2026.exceptions;

public class NotEnoughRightException extends RuntimeException {
    public NotEnoughRightException(String message) {
        super(message);
    }
}
