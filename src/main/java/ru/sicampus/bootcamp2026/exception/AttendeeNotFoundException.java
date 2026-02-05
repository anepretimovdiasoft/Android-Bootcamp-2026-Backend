package ru.sicampus.bootcamp2026.exception;

public class AttendeeNotFoundException extends RuntimeException {
    public AttendeeNotFoundException(Long meetingId, Long userId) {
        super("Attendee with userId " + userId + " not found in meeting " + meetingId);
    }
}
