package ru.sicampus.bootcamp2026.exception;

public class InvitationNotOwnedException extends RuntimeException {
    public InvitationNotOwnedException(String message) {
        super(message);
    }
}
