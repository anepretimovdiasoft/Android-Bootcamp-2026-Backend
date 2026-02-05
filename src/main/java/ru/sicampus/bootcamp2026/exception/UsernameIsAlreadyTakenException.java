package ru.sicampus.bootcamp2026.exception;

public class UsernameIsAlreadyTakenException extends RuntimeException {
    public UsernameIsAlreadyTakenException(String message) {
        super(message);
    }
}
