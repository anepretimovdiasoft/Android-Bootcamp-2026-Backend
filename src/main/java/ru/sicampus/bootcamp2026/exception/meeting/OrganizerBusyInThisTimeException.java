package ru.sicampus.bootcamp2026.exception.meeting;

public class OrganizerBusyInThisTimeException extends RuntimeException {
    public OrganizerBusyInThisTimeException(String message) {
        super(message);
    }
}
