package ru.sicampus.bootcamp2026.exception.invitation;

public class InvitationNotFoundException extends RuntimeException {
    public InvitationNotFoundException(String message) {
        super(message);
    }
}
