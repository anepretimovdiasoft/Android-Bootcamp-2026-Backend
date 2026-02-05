package ru.example.edu.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.example.edu.exception.DepartmentNotFoundException;
import ru.example.edu.exception.InviteNotFoundException;
import ru.example.edu.exception.MeetupNotFoundException;
import ru.example.edu.exception.PersonNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<String> handleDepartmentNotFoundException(DepartmentNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<String> handlePersonNotFoundException(PersonNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InviteNotFoundException.class)
    public ResponseEntity<String> handleInviteNotFoundException(InviteNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MeetupNotFoundException.class)
    public ResponseEntity<String> handleMeetupNotFoundException(MeetupNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
}
