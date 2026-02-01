package ru.sicampus.bootcamp2026.web.dto.meeting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.sicampus.bootcamp2026.web.dto.user.UserMiniDto;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@Schema(description = "Используется для отправки списков встреч")
public class MeetingMiniDto {

    @Schema(description = "Id встречи", example = "1")
    private long id;

    @Schema(description = "Название", example = "Планирование спринта")
    private String title;

    @Schema(description = "Описание", example = "Обсудим вопросы заказчика по UX дизайну андройд приложения")
    private String description;

    @Schema(description = "Адрес", example = "204 кабинет")
    private String address;

    @Schema(description = "Дата встречи", example = "2026-02-01")
    private LocalDate date;

    @Schema(description = "Время начала встречи", example = "19:00:00")
    private LocalTime timeStart;

    @Schema(description = "Время конца встречи", example = "21:00:00")
    private LocalTime timeEnd;

    // Организатор
    private UserMiniDto organizer;

}
