package ru.sicampus.bootcamp2026.exception.meeting;

public class EndBeforeStartMeetingException extends RuntimeException {
    public EndBeforeStartMeetingException(String message) {
        super(message);
    }
}
