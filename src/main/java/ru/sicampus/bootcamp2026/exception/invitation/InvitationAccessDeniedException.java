package ru.sicampus.bootcamp2026.exception.invitation;

public class InvitationAccessDeniedException extends RuntimeException {
    public InvitationAccessDeniedException(String message) {
        super(message);
    }
}
