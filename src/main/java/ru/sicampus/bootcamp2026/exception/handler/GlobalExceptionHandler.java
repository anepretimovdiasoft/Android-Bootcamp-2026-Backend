package ru.sicampus.bootcamp2026.exception.handler;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sicampus.bootcamp2026.exception.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<Map<String, Object>> buildResponse(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", message);
        body.put("status", status.value());
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MeetingNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleMeetingNotFound(MeetingNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAttendeeNotFound(AttendeeNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidMeetingTimeException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidMeetingTime(InvalidMeetingTimeException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidStatusException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidStatus(InvalidStatusException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
        return buildResponse("Email уже используется", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return buildResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvitationNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleInvitationNotFound(InvitationNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyExists(UserAlreadyExistsException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }


}
