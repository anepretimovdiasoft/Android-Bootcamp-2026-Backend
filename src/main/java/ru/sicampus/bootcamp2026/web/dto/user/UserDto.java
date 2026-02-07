package ru.sicampus.bootcamp2026.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Используется при получении полной информации о пользователе")
public class UserDto {

    @Schema(description = "Id пользователя", example = "1")
    private long id;

    @Schema(description = "Имя", example = "Андрей")
    private String firstName;

    @Schema(description = "Фамилия", example = "Петров")
    private String secondName;

    @Schema(description = "Почта", example = "limasov@gmail.com")
    private String email;

    @Schema(description = "Описание", example = "Это описание")
    private String description;

    @Schema(description = "Должность", example = "Front dev")
    private String position;

    @Schema(description = "Отдел", example = "Android приложений")
    private String department;

    @Schema(description = "Ссылка на аватарку", example = "https://catalog-cdn.detmir.st/media/2fe02057f9915e72a378795d32c79ea9.jpeg")
    private String photoUrl;

    @Schema(description = "Роль пользователя (ROLE_USER/ROLE_ADMIN)", example = "ROLE_ADMIN")
    private String role;

    @Schema(description = "Дата и время создания аккаунта", example = "2026-01-04T22:55:40.269177")
    private LocalDateTime createdAt;

    @Schema(description = "Дата и время последнего обновления аккаунта", example = "2026-01-11T22:55:40.269177")
    private LocalDateTime updatedAt;

}
