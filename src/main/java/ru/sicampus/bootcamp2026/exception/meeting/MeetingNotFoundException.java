package ru.sicampus.bootcamp2026.exception.meeting;

public class MeetingNotFoundException extends RuntimeException {
    public MeetingNotFoundException(String message) {
        super(message);
    }
}
