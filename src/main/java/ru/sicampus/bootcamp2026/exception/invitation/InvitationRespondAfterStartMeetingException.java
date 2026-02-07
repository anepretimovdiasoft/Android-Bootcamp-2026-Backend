package ru.sicampus.bootcamp2026.exception.invitation;

import org.springframework.http.HttpStatus;
import ru.sicampus.bootcamp2026.exception.ClientException;

public class InvitationRespondAfterStartMeetingException extends ClientException {
    public InvitationRespondAfterStartMeetingException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
