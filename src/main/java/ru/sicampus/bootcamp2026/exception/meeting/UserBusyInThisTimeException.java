package ru.sicampus.bootcamp2026.exception.meeting;

public class UserBusyInThisTimeException extends RuntimeException {
    public UserBusyInThisTimeException(String message) {
        super(message);
    }
}
