package ru.sicampus.bootcamp2026.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateInvitationException extends RuntimeException {
    public DuplicateInvitationException(String message) {
        super(message);
    }

    public DuplicateInvitationException(Long meetId, Long userId) {
        super(String.format("Invitation already exists for meetId: %d and userId: %d", meetId, userId));
    }
}