package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.toApp.MeetingDTO;
import ru.sicampus.bootcamp2026.dto.toApp.ParticipantDTO;
import ru.sicampus.bootcamp2026.entity.Meeting;

import java.util.stream.Collectors;

@UtilityClass
public class MeetingMapper {
    public static MeetingDTO convertToDto(Meeting meeting) {
        MeetingDTO dto = new MeetingDTO();
        dto.setId(meeting.getId());
        dto.setTitle(meeting.getTitle());
        dto.setDescription(meeting.getDescription());
        dto.setOrganizerId(meeting.getOrganizer().getId());
        dto.setOrganizerName(meeting.getOrganizer().getFullName());

        dto.setDate(meeting.getTimeSlot().getDate().getDate());
        dto.setStartTime(meeting.getTimeSlot().getStartTime());
        dto.setEndTime(meeting.getTimeSlot().getEndTime());

        dto.setParticipants(meeting.getParticipants()
                        .stream()
                        .map(participant -> {
                            ParticipantDTO pDto = new ParticipantDTO();
                            pDto.setId(participant.getParticipant().getId());
                            pDto.setFullName(participant.getParticipant().getFullName());
                            pDto.setStatus(participant.getParticipantStatus().getStatus());
                            return pDto;
                        })
                        .collect(Collectors.toList())
        );

        return dto;
    }

}
