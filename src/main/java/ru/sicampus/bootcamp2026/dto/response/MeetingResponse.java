package ru.sicampus.bootcamp2026.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sicampus.bootcamp2026.model.Meeting;
import ru.sicampus.bootcamp2026.model.MeetingStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingResponse {

    private UUID id;
    private UUID organizerId;
    private String organizerUsername;
    private String title;
    private String description;
    private String location;
    private Instant startTime;
    private Instant endTime;
    private MeetingStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private List<ParticipantResponse> participants;

    public static MeetingResponse fromMeeting(Meeting meeting) {
        return MeetingResponse.builder()
                .id(meeting.getId())
                .organizerId(meeting.getOrganizer_id().getId())
                .organizerUsername(meeting.getOrganizer_id().getUsername())
                .title(meeting.getTitle())
                .description(meeting.getDescription())
                .location(meeting.getLocation())
                .startTime(meeting.getStartTime())
                .endTime(meeting.getEndTime())
                .status(meeting.getMeetingStatus())
                .createdAt(meeting.getCreatedAt())
                .updatedAt(meeting.getUpdatedAt())
                .participants(meeting.getMeetingParticipants().stream()
                        .map(ParticipantResponse::fromParticipant)
                        .collect(Collectors.toList()))
                .build();
    }

}
