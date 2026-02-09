package ru.sicampus.bootcamp2026.exception;

public class WrongDateFormatException extends RuntimeException {
    public WrongDateFormatException(String message) {
        super(message);
    }
}
