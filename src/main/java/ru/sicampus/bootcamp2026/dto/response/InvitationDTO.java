package ru.sicampus.bootcamp2026.dto.response;

import ru.sicampus.bootcamp2026.entity.InvitationStatus;

import java.time.Instant;

public class InvitationDTO {
    private long id;
    private MeetingDTO meeting;
    private UserResponseDTO user;
    private InvitationStatus status;
    private Instant createdAt;
    private Instant respondedAt;
}
