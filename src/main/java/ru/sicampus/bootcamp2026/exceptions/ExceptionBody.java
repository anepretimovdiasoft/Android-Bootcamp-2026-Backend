package ru.sicampus.bootcamp2026.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@Schema(description = "Используется для ошибок")
public class ExceptionBody {

    @Schema(description = "Главное сообщение об ошибке")
    private String message;

    @Schema(description = "Несколько побочных ошибок")
    private Map<String, String> errors;

    public ExceptionBody(String message) {
        this.message = message;
    }
}
