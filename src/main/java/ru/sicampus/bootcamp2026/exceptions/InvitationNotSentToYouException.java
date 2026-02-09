package ru.sicampus.bootcamp2026.exceptions;

public class InvitationNotSentToYouException extends RuntimeException {
    public InvitationNotSentToYouException(String message) {
        super(message);
    }
}
