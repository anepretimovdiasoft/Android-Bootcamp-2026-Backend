package ru.sicampus.bootcamp2026.exception.user;

import org.springframework.http.HttpStatus;
import ru.sicampus.bootcamp2026.exception.ClientException;

public class UserNotFoundException extends ClientException {
    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
