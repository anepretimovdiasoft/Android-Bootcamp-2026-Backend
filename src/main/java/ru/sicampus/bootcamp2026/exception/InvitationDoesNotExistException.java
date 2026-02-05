package ru.sicampus.bootcamp2026.exception;

public class InvitationDoesNotExistException extends RuntimeException {
    public InvitationDoesNotExistException(String message) {
        super(message);
    }
}
