package ru.sicampus.bootcamp2026.exception;

public class LoginDataEmailAlreadyTakenException extends RuntimeException {
    public LoginDataEmailAlreadyTakenException(String message) {
        super(message);
    }
}
