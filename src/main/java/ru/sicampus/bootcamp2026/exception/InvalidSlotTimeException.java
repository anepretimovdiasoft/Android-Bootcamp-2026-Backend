package ru.sicampus.bootcamp2026.exception;

public class InvalidSlotTimeException extends RuntimeException {
    public InvalidSlotTimeException(String message) {
        super(message);
    }
}
