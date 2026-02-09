package ru.sicampus.bootcamp2026.exception;

public class MeetingOverlapException extends RuntimeException {
    public MeetingOverlapException(String message) {
        super(message);
    }
}
