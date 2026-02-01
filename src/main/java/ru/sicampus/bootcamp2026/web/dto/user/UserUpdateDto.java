package ru.sicampus.bootcamp2026.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@Schema(description = "Используется при редактировании профиля")
public class UserUpdateDto {

    @Schema(description = "Id пользователя", example = "1")
    private long id;

    @Schema(description = "Имя", example = "Андрей")
    @NotNull(message = "Имя не может быть пустым.")
    @Length(max = 20, message = "Максимальная длина имени 20 символов.")
    private String firstName;

    @Schema(description = "Фамилия", example = "Петров")
    @NotNull(message = "Фамилия не может быть пустой.")
    @Length(max = 20, message = "Максимальная длина фамилии 20 символов.")
    private String secondName;

    @Schema(description = "Описание", example = "Это описание")
    @Length(max = 140, message = "Максимальная длина описания 140 символов.")
    private String description;

    @Schema(description = "Должность", example = "Front dev")
    @Length(max = 30, message = "Максимальная длина описания 30 символов.")
    private String position;

    @Schema(description = "Отдел", example = "Android приложений")
    @Length(max = 30, message = "Максимальная длина описания 30 символов.")
    private String department;

}
