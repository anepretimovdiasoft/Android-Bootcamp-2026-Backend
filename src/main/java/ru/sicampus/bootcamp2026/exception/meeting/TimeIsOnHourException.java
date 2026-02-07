package ru.sicampus.bootcamp2026.exception.meeting;

import org.springframework.http.HttpStatus;
import ru.sicampus.bootcamp2026.exception.ClientException;

public class TimeIsOnHourException extends ClientException {
    public TimeIsOnHourException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
