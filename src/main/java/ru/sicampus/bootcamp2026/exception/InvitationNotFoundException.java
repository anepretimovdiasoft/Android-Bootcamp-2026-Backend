package ru.sicampus.bootcamp2026.exception;

public class InvitationNotFoundException extends RuntimeException {
    public InvitationNotFoundException() {
        super("Invitation not found!");
    }
}