package ru.sicampus.bootcamp2026.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.sicampus.bootcamp2026.validation.HourStepTime;

import java.time.Instant;

@Data
public class MeetingCreateDTO {
    @NotNull
    @NotBlank
    private String title;

    private String description;

    @NotNull
    @HourStepTime
    private Instant timeStart;

    @NotNull
    @HourStepTime
    private Instant timeEnd;
}

