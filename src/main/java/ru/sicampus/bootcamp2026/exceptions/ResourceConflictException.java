package ru.sicampus.bootcamp2026.exceptions;

public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message) {
        super(message);
    }
}
