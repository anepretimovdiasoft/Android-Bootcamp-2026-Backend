package ru.sicampus.bootcamp2026.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.sicampus.bootcamp2026.exception.ClientException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<String> handleClientException(ClientException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(e.getMessage());
    }
}