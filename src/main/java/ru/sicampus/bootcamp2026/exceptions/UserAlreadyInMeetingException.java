package ru.sicampus.bootcamp2026.exceptions;

public class UserAlreadyInMeetingException extends RuntimeException {
    public UserAlreadyInMeetingException(String message) {
        super(message);
    }
}
