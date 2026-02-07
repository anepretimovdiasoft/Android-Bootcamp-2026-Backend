package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.InvitationStatus;
import ru.sicampus.bootcamp2026.entity.Meeting;

@UtilityClass
public class InvitationMapper {
    public InvitationDTO convertToDto(Invitation invitation) {
        InvitationDTO dto = new InvitationDTO();
        dto.setId(invitation.getId());
        dto.setMeetingId(invitation.getMeeting().getId());
        dto.setPersonName(invitation.getPerson().getName());
        dto.setStatus(invitation.getStatus().name());
        dto.setRespondedAt(invitation.getRespondedAt());
        dto.setCreatedAt(invitation.getCreatedAt());
        return dto;
    }

    public static InvitationStatus toStatus(String status) {
        return InvitationStatus.valueOf(status.toUpperCase());
    }
}
