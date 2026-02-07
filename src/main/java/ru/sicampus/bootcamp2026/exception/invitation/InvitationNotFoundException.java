package ru.sicampus.bootcamp2026.exception.invitation;

import org.springframework.http.HttpStatus;
import ru.sicampus.bootcamp2026.exception.ClientException;

public class InvitationNotFoundException extends ClientException {
    public InvitationNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
