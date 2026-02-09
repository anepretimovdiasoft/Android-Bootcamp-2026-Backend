package ru.sicampus.bootcamp2026.exceptions;

public class InvitationNotExistException extends RuntimeException {
    public InvitationNotExistException(String message) {
        super(message);
    }
}
