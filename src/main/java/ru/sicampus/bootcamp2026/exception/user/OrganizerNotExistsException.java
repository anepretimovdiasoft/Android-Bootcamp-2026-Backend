package ru.sicampus.bootcamp2026.exception.user;

public class OrganizerNotExistsException extends RuntimeException {
    public OrganizerNotExistsException(String message) {
        super(message);
    }
}
