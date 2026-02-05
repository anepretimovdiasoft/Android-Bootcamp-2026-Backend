package ru.sicampus.bootcamp2026.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sicampus.bootcamp2026.dto.ExceptionResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFound(ResourceNotFoundException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("NOT FOUND", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequest(BadRequestException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("BAD REQUEST", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExists(BadRequestException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("ALREADY EXISTS", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }
}
