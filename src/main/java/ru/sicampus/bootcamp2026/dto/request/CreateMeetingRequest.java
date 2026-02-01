package ru.sicampus.bootcamp2026.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMeetingRequest {

    @NotBlank(message = "Название встречи обязательно")
    @Size(max = 50, message = "Название встречи не должно превышать 50 символов")
    private String title;

    @Size(max = 2048, message = "Описание не должно превышать 2048 символов")
    private String description;

    @Size(max = 255, message = "Местоположение не должно превышать 255 символов")
    private String location;

    @NotNull(message = "Время начала обязательно")
    private Instant startTime;

    @NotNull(message = "Время окончания обязательно")
    private Instant endTime;

    @Size(min = 1, message = "Должен быть хотя бы один участник")
    @NotNull(message = "Список участников обязателен")
    private List<UUID> participantIds;
}
