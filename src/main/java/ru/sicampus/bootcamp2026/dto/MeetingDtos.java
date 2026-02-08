package ru.sicampus.bootcamp2026.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public class MeetingDtos {

    /** Участник встречи: данные пользователя + статус приглашения */
    public record MeetingParticipantResponse(
            Long id,
            String position,
            String name,
            String email,
            String phone,
            LocalDate birthDate,
            String avatarUrl,
            String invitationStatus
    ) {}

    public record CreateMeetingRequest(
            @NotBlank @Size(max = 255) String title,
            String description,
            @NotNull OffsetDateTime startsAt,
            @NotNull OffsetDateTime endsAt,
            @Size(min = 1) List<Long> invitedUserIds

    ) {}

    public record UpdateMeetingRequest(
            @NotBlank @Size(max = 255) String title,
            String description,
            @NotNull OffsetDateTime startsAt,
            @NotNull OffsetDateTime endsAt

    ) {}

    public record MeetingResponse(
            Long id,
            String title,
            OffsetDateTime startsAt,
            OffsetDateTime endsAt,
            String colorHex,
            String description
    ) {}
}