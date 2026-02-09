package ru.sicampus.bootcamp2026.exception;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String message) {
        super(message);
    }
    public InvalidStatusException() {
        super("Invalid status!");
    }
}
