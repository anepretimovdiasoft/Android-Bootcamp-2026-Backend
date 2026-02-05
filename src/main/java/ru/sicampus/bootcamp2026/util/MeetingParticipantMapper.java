package ru.sicampus.bootcamp2026.util;

import ru.sicampus.bootcamp2026.dto.MeetingParticipantDTO;
import ru.sicampus.bootcamp2026.entity.MeetingParticipant;

public class MeetingParticipantMapper {

    public static MeetingParticipantDTO toDto(MeetingParticipant participant) {
        if (participant == null) return null;

        Long meetingId = participant.getMeeting() != null ?
                participant.getMeeting().getId() : null;

        Long userId = participant.getUser() != null ?
                participant.getUser().getId() : null;

        return new MeetingParticipantDTO(
                participant.getId(),
                meetingId,
                userId,
                participant.getStatus()
        );
    }
}