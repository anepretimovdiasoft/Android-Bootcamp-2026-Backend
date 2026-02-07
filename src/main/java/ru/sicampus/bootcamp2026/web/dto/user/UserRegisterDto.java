package ru.sicampus.bootcamp2026.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@Schema(description = "Используется при регистрации пользователя")
public class UserRegisterDto {

    @Schema(description = "Почта", example = "limasov@gmail.com")
    @NotNull(message = "Почта не может быть пустой.")
    @Length(max = 255, message = "Почта не должна быть больше 255 символов.")
    @Email(message = "Неверный формат почты.")
    private String email;

    @Schema(description = "Пароль", example = "11111111")
    @NotNull(message = "Пароль не может быть пустым.")
    @Length(min = 8, max = 64, message = "Длина пароля должна быть от 8 до 64 символов.")
    private String password;

    @Schema(description = "Имя", example = "Андрей")
    @NotNull(message = "Имя не может быть пустым.")
    @Length(min = 1, max = 20, message = "Длина имени от 1 до 20 символов.")
    private String firstName;

    @Schema(description = "Фамилия", example = "Петров")
    @NotNull(message = "Фамилия не может быть пустой.")
    @Length(min = 1, max = 20, message = "Длина фамилии от 1 до 20 символов.")
    private String secondName;

}
