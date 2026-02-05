package ru.sicampus.bootcamp2026.exception;

public class InvalidMeetingDateException extends RuntimeException {
    public InvalidMeetingDateException(String message) {
        super(message);
    }
}
