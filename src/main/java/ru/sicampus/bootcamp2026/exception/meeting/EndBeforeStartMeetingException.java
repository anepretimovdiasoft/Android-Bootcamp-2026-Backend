package ru.sicampus.bootcamp2026.exception.meeting;

import org.springframework.http.HttpStatus;
import ru.sicampus.bootcamp2026.exception.ClientException;

public class EndBeforeStartMeetingException extends ClientException {
    public EndBeforeStartMeetingException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
