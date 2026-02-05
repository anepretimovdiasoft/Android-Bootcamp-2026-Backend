package ru.sicampus.bootcamp2026.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.sicampus.bootcamp2026.entity.InvitationStatus;

import java.time.Instant;

@AllArgsConstructor
@Data
public class InvitationResponseDTO {
    private long id;
    private InvitationStatus status;

    private Instant createdAt;
    private Instant respondedAt;

    private MeetingResponseDTO meeting;
}
