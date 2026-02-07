package ru.sicampus.bootcamp2026.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.sicampus.bootcamp2026.exception.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<String> handleDepartmentNotFoundException(DepartmentNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<String> handlePersonNotFoundException(PersonNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvitationNotFoundException.class)
    public ResponseEntity<String> handleInvitationNotFoundException(InvitationNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MeetingNotFoundException.class)
    public ResponseEntity<String> handleMeetingNotFoundException(MeetingNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidMeetingTimeException.class)
    public ResponseEntity<String> handleInvalidMeetingTimeException(InvalidMeetingTimeException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersonAlreadyExistsException.class)
    public ResponseEntity<String> handlePersonAlreadyExistsException(PersonAlreadyExistsException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PersonAuthorityNotFoundException.class)
    public ResponseEntity<String> handlePersonAuthorityNotFoundException(PersonAuthorityNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthorityNotFoundException.class)
    public ResponseEntity<String> handleAuthorityNotFoundException(AuthorityNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
