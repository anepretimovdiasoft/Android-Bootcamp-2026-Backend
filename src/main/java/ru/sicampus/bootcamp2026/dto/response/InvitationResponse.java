package ru.sicampus.bootcamp2026.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sicampus.bootcamp2026.model.ParticipantStatus;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationResponse {

    private UUID id;
    private UUID meetingId;
    private String meetingTitle;
    private String meetingDescription;
    private String meetingLocation;
    private Instant meetingStartTime;
    private Instant meetingEndTime;
    private String organizerUsername;
    private ParticipantStatus status;
    private Instant createdAt;
}
