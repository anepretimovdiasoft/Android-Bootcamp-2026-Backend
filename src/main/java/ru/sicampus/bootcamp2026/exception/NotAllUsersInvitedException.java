package ru.sicampus.bootcamp2026.exception;

public class NotAllUsersInvitedException extends RuntimeException {
    public NotAllUsersInvitedException(String message) {
        super(message);
    }
}
