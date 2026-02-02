package ru.sicampus.bootcamp2026.handler;

import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.sicampus.bootcamp2026.dto.ErrorResponse;
import ru.sicampus.bootcamp2026.exception.ErrorCode;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected @NonNull ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        Map<String, Object> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        org.springframework.validation.FieldError::getField,
                        this::convertToValidationError
                ));
        return ResponseEntity.status(422).body(ErrorResponse.builder() // Zaglushka™
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .code(ErrorCode.VALIDATION_ERROR)
                .error(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                .message("Some fields failed validation")
                .path(request.getContextPath())
                .timestamp(Instant.now())
                .details(errors)
                .build());
    }

    private ErrorResponse.FieldError convertToValidationError(FieldError error) {
        return new ErrorResponse.FieldError(error.getRejectedValue(), error.getDefaultMessage());
    }
}
