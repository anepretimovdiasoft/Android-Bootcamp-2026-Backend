package ru.sicampus.bootcamp2026.exception;

public class LoginDataEmailNotFoundException extends RuntimeException {
    public LoginDataEmailNotFoundException(String message) {
        super(message);
    }
}
