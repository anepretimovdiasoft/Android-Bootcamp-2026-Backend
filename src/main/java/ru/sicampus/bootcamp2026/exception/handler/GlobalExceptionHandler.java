package ru.sicampus.bootcamp2026.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.sicampus.bootcamp2026.exception.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LoginDataEmailNotFoundException.class)
    public ResponseEntity<String> handleLoginDataEmailNotFoundException(LoginDataEmailNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MeetingNotFoundException.class)
    public ResponseEntity<String> handleMeetingNotFoundException(MeetingNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LoginDataEmailAlreadyTakenException.class)
    public ResponseEntity<String> handleLoginDataEmailAlreadyTakenException(LoginDataEmailAlreadyTakenException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AlreadyBookedTimeSlotException.class)
    public ResponseEntity<String> handleAlreadyBookedTimeSlotException(AlreadyBookedTimeSlotException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidTimeRangeException.class)
    public ResponseEntity<String> handleInvalidTimeRangeException(InvalidTimeRangeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
