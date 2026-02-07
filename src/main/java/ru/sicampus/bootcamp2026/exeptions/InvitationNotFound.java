package ru.sicampus.bootcamp2026.exeptions;

public class InvitationNotFound extends RuntimeException {
    public InvitationNotFound(String message) {
        super(message);
    }
}
