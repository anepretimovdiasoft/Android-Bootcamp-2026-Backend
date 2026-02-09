package ru.sicampus.bootcamp2026.exception;

public class WrongTimeFormatException extends RuntimeException {
    public WrongTimeFormatException(String message) {
        super(message);
    }
}
