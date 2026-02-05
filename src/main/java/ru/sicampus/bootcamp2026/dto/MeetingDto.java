package ru.sicampus.bootcamp2026.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.sicampus.bootcamp2026.entity.Meeting;

import java.time.LocalDateTime;

@Data
public final class MeetingDto {
    private Long id;
    private @NotBlank String organizerName;
    private @NotBlank String organizerLastName;
    private @NotBlank String title;
    private @NotNull LocalDateTime startAt;
    private @NotNull LocalDateTime endAt;
    private String description;
    private Meeting.@NotNull Type type;
    private String location;
    private String url;
}
