package ru.sicampus.bootcamp2026.exception.invitation;

import org.springframework.http.HttpStatus;
import ru.sicampus.bootcamp2026.exception.ClientException;

public class InvitationAlreadyRespondedException extends ClientException {
    public InvitationAlreadyRespondedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
