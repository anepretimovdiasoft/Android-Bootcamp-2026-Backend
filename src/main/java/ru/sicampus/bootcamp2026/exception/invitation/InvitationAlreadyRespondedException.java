package ru.sicampus.bootcamp2026.exception.invitation;

public class InvitationAlreadyRespondedException extends RuntimeException {
    public InvitationAlreadyRespondedException(String message) {
        super(message);
    }
}
