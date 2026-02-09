package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;

@UtilityClass
public class InvitationMapper {
    public InvitationDTO convertToDto(Invitation invitation) {
        InvitationDTO dto = new InvitationDTO();
        dto.setId(invitation.getId());
        dto.setMeetingId(invitation.getMeeting().getId());
        dto.setTargetEmail(invitation.getTarget().getEmail());
        dto.setStatus(invitation.getStatus());
        dto.setCreatedAt(invitation.getCreatedAt());
        return dto;
    }
}
