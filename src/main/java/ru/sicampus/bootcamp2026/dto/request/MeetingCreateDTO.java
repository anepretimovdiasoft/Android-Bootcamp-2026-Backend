package ru.sicampus.bootcamp2026.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.sicampus.bootcamp2026.validation.HourStepTime;

import java.time.Instant;

@Data
public class MeetingCreateDTO {
    @NotBlank
    private long organizerId; // TODO: make organizer = current user

    @NotBlank
    private String title;

    private String description;

    @NotBlank
    @HourStepTime
    private Instant timeStart;

    @NotBlank
    @HourStepTime
    private Instant timeEnd;
}

