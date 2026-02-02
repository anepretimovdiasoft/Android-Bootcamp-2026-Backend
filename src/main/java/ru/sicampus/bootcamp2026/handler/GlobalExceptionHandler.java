package ru.sicampus.bootcamp2026.handler;

import jakarta.servlet.ServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.sicampus.bootcamp2026.dto.ErrorResponse;
import ru.sicampus.bootcamp2026.exception.BaseException;
import ru.sicampus.bootcamp2026.exception.ErrorCode;

import java.time.Instant;

import static org.apache.catalina.util.FilterUtil.getRequestPath;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(
            BaseException ex,
            ServletRequest request) {

        HttpStatus status = determineHttpStatus(ex.getErrorCode());

        ErrorResponse error = ErrorResponse.builder()
                .status(status.value())
                .code(ex.getErrorCode())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(getRequestPath(request))
                .timestamp(Instant.now())
                .details(ex.getDetails())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    private HttpStatus determineHttpStatus(ErrorCode errorCode) {
        return switch (errorCode) {
            case USER_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case USER_ALREADY_EXISTS -> HttpStatus.CONFLICT;
            default -> HttpStatus.BAD_REQUEST;
        };
    }
}
