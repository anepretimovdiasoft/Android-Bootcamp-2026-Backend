package ru.sicampus.bootcamp2026.exceptions;

public class InvitationHasAlreadyRespondedException extends RuntimeException {
    public InvitationHasAlreadyRespondedException(String message) {
        super(message);
    }
}
