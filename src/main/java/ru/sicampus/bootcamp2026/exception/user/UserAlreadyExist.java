package ru.sicampus.bootcamp2026.exception.user;

import org.springframework.http.HttpStatus;
import ru.sicampus.bootcamp2026.exception.ClientException;

public class UserAlreadyExist extends ClientException {
    public UserAlreadyExist(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
