package ru.sicampus.bootcamp2026.web.dto.meeting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@Schema(description = "Используется для создания встречи")
public class MeetingCreateDto {

    @Schema(description = "Название", example = "Планирование спринта")
    @NotNull(message = "Название не может быть пустым.")
    @Length(min = 1, max = 50, message = "Длина названия от 1 до 50 символов.")
    private String title;

    @Schema(description = "Адрес", example = "204 кабинет")
    @NotNull(message = "Адрес не может быть пустым.")
    @Length(min = 1, max = 50, message = "Длина адреса от 1 до 50 символов.")
    private String address;

    @Schema(description = "Описание", example = "Обсудим вопросы заказчика по UX дизайну андройд приложения")
    @Length(max = 50, message = "Длина описания максимум 140 символов.")
    private String description;

    @Schema(description = "Дата встречи", example = "2026-02-01")
    @NotNull(message = "Дата не может быть пустой.")
    private LocalDate date;

    @Schema(description = "Время начала встречи", example = "19:00:00")
    @NotNull(message = "Время начала не может быть пустым.")
    private LocalTime timeStart;

    @Schema(description = "Время конца встречи", example = "21:00:00")
    @NotNull(message = "Время конца не может быть пустым.")
    private LocalTime timeEnd;

    @Schema(description = "id приглашенных пользователей")
    private List<Long> usersId;

}
