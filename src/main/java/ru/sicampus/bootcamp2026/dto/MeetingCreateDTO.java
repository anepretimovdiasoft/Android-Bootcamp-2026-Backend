package ru.sicampus.bootcamp2026.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class MeetingCreateDTO {
    @NotBlank(message = "Meeting name cannot be blank")
    private String name;
    @NotBlank(message = "Meeting description cannot be blank")
    private String description;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
}