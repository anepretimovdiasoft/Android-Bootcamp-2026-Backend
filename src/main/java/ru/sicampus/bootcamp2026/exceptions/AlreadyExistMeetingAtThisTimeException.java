package ru.sicampus.bootcamp2026.exceptions;

public class AlreadyExistMeetingAtThisTimeException extends RuntimeException {
    public AlreadyExistMeetingAtThisTimeException(String message) {
        super(message);
    }
}
