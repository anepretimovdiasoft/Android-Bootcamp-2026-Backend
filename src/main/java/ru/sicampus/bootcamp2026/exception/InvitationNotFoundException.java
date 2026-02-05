package ru.sicampus.bootcamp2026.exception;

public class InvitationNotFoundException extends RuntimeException {
    public InvitationNotFoundException(String message) {
        super(message);
    }

    public InvitationNotFoundException() {
        super("Invitation not found");
    }
}