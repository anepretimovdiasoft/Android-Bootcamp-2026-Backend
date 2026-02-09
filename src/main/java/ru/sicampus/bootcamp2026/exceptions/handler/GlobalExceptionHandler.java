package ru.sicampus.bootcamp2026.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.sicampus.bootcamp2026.exceptions.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<String> handleEmailAlreadyUsedException(EmailAlreadyUsedException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MeetingNotExistException.class)
    public ResponseEntity<String> handleMeetingNotExist(MeetingNotExistException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistMeetingAtThisTimeException.class)
    public ResponseEntity<String> handleAlreadyExistMeetingAtThisTimeException(AlreadyExistMeetingAtThisTimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<String> handlePasswordNotMatchException(PasswordNotMatchException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvitationHasAlreadyRespondedException.class)
    public ResponseEntity<String> handleInvitationHasAlreadyResponded(InvitationHasAlreadyRespondedException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvitationNotExistException.class)
    public ResponseEntity<String> handleInvitationNotExist(InvitationNotExistException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvitationNotSentToYouException.class)
    public ResponseEntity<String> handleInvitationNotSentToYou(InvitationNotSentToYouException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotEnoughRightException.class)
    public ResponseEntity<String> handleNotEnoughRightException(NotEnoughRightException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserAlreadyInMeetingException.class)
    public ResponseEntity<String> handleUserAlreadyInMeetingException(UserAlreadyInMeetingException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvitationAlreadyExistsException.class)
    public ResponseEntity<String> handleInvitationAlreadyExistsException(InvitationAlreadyExistsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }
}
