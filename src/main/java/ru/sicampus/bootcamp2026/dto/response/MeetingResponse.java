package ru.sicampus.bootcamp2026.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sicampus.bootcamp2026.model.MeetingStatus;
import ru.sicampus.bootcamp2026.model.ParticipantStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingResponse {

    private UUID id;
    private String title;
    private String description;
    private String location;
    private Instant startTime;
    private Instant endTime;
    private MeetingStatus status;
    private Instant createdAt;
    private OrganizerInfo organizer;
    private List<ParticipantInfo> participants;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrganizerInfo {
        private UUID id;
        private String username;
        private String email;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParticipantInfo {
        private UUID id;
        private String username;
    }

}
