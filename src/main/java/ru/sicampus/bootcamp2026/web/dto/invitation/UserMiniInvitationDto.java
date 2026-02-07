package ru.sicampus.bootcamp2026.web.dto.invitation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Используется для отправки списка пользователей со статусом приглашения")
public class UserMiniInvitationDto {

    // User
    @Schema(description = "Id пользователя", example = "1")
    private long id;

    @Schema(description = "Имя", example = "Андрей")
    private String firstName;

    @Schema(description = "Фамилия", example = "Петров")
    private String secondName;

    @Schema(description = "Ссылка на аватарку", example = "https://catalog-cdn.detmir.st/media/2fe02057f9915e72a378795d32c79ea9.jpeg")
    private String photoUrl;

    // Invitations
    @Schema(description = "Статус приглашения (PENDING, ACCEPTED, DECLINED)", example = "ACCEPTED")
    private String status;

    @Schema(description = "Время ответа на приглашение", example = "2026-01-11T22:55:40.269177")
    private LocalDateTime respondedAt;

}

