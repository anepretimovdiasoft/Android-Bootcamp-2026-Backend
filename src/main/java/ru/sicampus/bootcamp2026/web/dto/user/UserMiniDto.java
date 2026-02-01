package ru.sicampus.bootcamp2026.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Используется при отправки больших списков пользователей")
public class UserMiniDto {

    @Schema(description = "Id пользователя", example = "1")
    private long id;

    @Schema(description = "Имя", example = "Андрей")
    private String firstName;

    @Schema(description = "Фамилия", example = "Петров")
    private String secondName;

    @Schema(description = "Ссылка на аватарку", example = "https://catalog-cdn.detmir.st/media/2fe02057f9915e72a378795d32c79ea9.jpeg")
    private String photoUrl;

}
