package ru.sicampus.bootcamp2026.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {

    @NotBlank(message = "Текущий пароль обязателен")
    private String currentPassword;

    @NotBlank(message = "Новый пароль обязателен")
    @Size(min = 6, max = 255, message = "Пароль должен быть от 6 до 255 символов")
    private String newPassword;

    @NotBlank(message = "Подтверждение нового пароля обязательно")
    private String confirmPassword;
}
