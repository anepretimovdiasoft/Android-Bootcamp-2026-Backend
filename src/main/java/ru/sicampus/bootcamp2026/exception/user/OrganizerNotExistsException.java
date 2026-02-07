package ru.sicampus.bootcamp2026.exception.user;

import org.springframework.http.HttpStatus;
import ru.sicampus.bootcamp2026.exception.ClientException;

public class OrganizerNotExistsException extends ClientException {
    public OrganizerNotExistsException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
