package ru.sicampus.bootcamp2026.exception;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String status) {
        super("Invalid status: " + status);
    }
}
