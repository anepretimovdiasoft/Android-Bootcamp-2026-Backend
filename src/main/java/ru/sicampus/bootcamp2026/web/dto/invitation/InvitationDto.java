package ru.sicampus.bootcamp2026.web.dto.invitation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@Schema(description = "Используется для получения информации о приглашении")
public class InvitationDto {

    @Schema(description = "Id приглашения", example = "1")
    private long id;

    // Author
    @Schema(description = "Id автора встречи", example = "1")
    private long authorId;

    @Schema(description = "Имя автора встречи", example = "Иван")
    private String authorFirstName;

    @Schema(description = "Фамилия автора встречи", example = "Иванов")
    private String authorSecondName;

    // Meeting
    @Schema(description = "Id встречи", example = "1")
    private long meetingId;

    @Schema(description = "Название", example = "Планирование спринта")
    private String title;

    @Schema(description = "Адрес", example = "204 кабинет")
    private String address;

    @Schema(description = "Дата встречи", example = "2026-02-01")
    private LocalDate date;

    @Schema(description = "Время начала встречи", example = "19:00:00")
    private LocalTime timeStart;

    @Schema(description = "Время конца встречи", example = "21:00:00")
    private LocalTime timeEnd;

}
