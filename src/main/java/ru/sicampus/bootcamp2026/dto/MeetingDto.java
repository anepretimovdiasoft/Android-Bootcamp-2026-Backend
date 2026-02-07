package ru.sicampus.bootcamp2026.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.sicampus.bootcamp2026.enums.MeetingType;

import java.time.LocalDateTime;
import java.util.List;

@Data
public final class MeetingDto {
    private Long id;
    private @NotBlank String title;
    private @NotNull LocalDateTime startAt;
    private @NotNull LocalDateTime endAt;
    private String description;
    private MeetingType type;
    private String location;
    private String url;
    private List<String> inviteeLogins;
}
