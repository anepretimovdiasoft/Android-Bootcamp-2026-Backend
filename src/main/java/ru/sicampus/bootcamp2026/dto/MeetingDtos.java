package ru.sicampus.bootcamp2026.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.List;

public class MeetingDtos {

    public record CreateMeetingRequest(
            @NotBlank @Size(max = 255) String title,
            @NotNull OffsetDateTime startsAt,
            @NotNull OffsetDateTime endsAt,
            @Size(min = 1) List<Long> invitedUserIds

    ) {}

    public record UpdateMeetingRequest(
            @NotBlank @Size(max = 255) String title,
            @NotNull OffsetDateTime startsAt,
            @NotNull OffsetDateTime endsAt

    ) {}

    public record MeetingResponse(Long id, String title, OffsetDateTime startsAt, OffsetDateTime endsAt) {}
}