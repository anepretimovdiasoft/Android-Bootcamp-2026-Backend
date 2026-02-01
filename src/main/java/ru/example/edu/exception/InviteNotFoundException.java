package ru.example.edu.exception;

public class InviteNotFoundException extends RuntimeException {
    public InviteNotFoundException(String message) {
        super(message);
    }
}
