package ru.sicampus.bootcamp2026.exception;

public class InvalidMeetingTimeException extends RuntimeException {
    public InvalidMeetingTimeException(String message) {
        super(message);
    }
}
