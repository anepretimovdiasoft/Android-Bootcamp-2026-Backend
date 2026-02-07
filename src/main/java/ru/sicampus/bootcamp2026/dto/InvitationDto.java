package ru.sicampus.bootcamp2026.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.sicampus.bootcamp2026.enums.InvitationStatus;
import ru.sicampus.bootcamp2026.enums.MeetingType;

import java.time.LocalDateTime;

@Data
public final class InvitationDto {
    private Long id;
    private Long meetingId;
    private @NotNull LocalDateTime meetingStartAt;
    private @NotNull LocalDateTime meetingEndAt;
    private @NotNull MeetingType meetingType;
    private Long inviteeId;
    private Long organizerId;
    private @NotBlank String organizerName;
    private @NotBlank String organizerLastname;
    private @NotNull InvitationStatus status;
}