package ru.sicampus.bootcamp2026.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sicampus.bootcamp2026.model.MeetingParticipant;
import ru.sicampus.bootcamp2026.model.ParticipantStatus;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantResponse {

    private UUID userId;
    private String username;
    private ParticipantStatus status;

    public static ParticipantResponse fromParticipant(MeetingParticipant participant) {
        return ParticipantResponse.builder()
                .userId(participant.getUser().getId())
                .username(participant.getUser().getUsername())
                .status(participant.getStatus())
                .build();
    }
}
