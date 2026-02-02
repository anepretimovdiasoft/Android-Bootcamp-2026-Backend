package ru.sicampus.bootcamp2026.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sicampus.bootcamp2026.dto.ApiResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseDTO> handleRuntime(RuntimeException ex) {
        return ResponseEntity.badRequest().body(new ApiResponseDTO(ex.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO> handleGlobal(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponseDTO("Ошибка на сервере: " + ex.getMessage()));
    }
}