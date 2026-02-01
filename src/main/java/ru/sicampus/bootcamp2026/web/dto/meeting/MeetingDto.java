package ru.sicampus.bootcamp2026.web.dto.meeting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.sicampus.bootcamp2026.web.dto.user.UserMiniDto;
import ru.sicampus.bootcamp2026.web.dto.user.UserMiniInvitationDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Schema(description = "Используется для отправки полной информации о встрече")
public class MeetingDto {

    @Schema(description = "Id встречи", example = "1")
    private long id;

    @Schema(description = "Название", example = "Планирование спринта")
    private String title;

    @Schema(description = "Адрес", example = "204 кабинет")
    private String address;

    @Schema(description = "Описание", example = "Обсудим вопросы заказчика по UX дизайну андройд приложения")
    private String description;

    @Schema(description = "Дата встречи", example = "2026-02-01")
    private LocalDate date;

    @Schema(description = "Время начала встречи", example = "19:00:00")
    private LocalDateTime timeStart;

    @Schema(description = "Время конца встречи", example = "21:00:00")
    private LocalDateTime timeEnd;

    // Организатор
    private UserMiniDto organizer;

    // Приглашенные пользователи (первые 10) (со статусом приглашения)
    private List<UserMiniInvitationDto> users;

    @Schema(description = "Время создания встречи", example = "2026-02-01 19:09:40.160401")
    private LocalDateTime createAt;

}
