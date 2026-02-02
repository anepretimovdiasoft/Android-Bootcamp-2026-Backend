package ru.sicampus.bootcamp2026.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import ru.sicampus.bootcamp2026.exception.ErrorCode;

import java.time.Instant;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private int status;
    private ErrorCode code;
    private String error;
    private String message;
    private String path;
    private Instant timestamp;
    private Map<String, Object> details;

    public static ErrorResponse of(HttpStatus status, String message, String path) {
        return ErrorResponse.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .timestamp(Instant.now())
                .build();
    }

    public static ErrorResponse of(HttpStatus status, String message, String path,
                                   Map<String, Object> details) {
        return ErrorResponse.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .timestamp(Instant.now())
                .details(details)
                .build();
    }

    @AllArgsConstructor
    @Data
    public static class FieldError {
        private Object rejectedValue;
        private String message;
    }
}