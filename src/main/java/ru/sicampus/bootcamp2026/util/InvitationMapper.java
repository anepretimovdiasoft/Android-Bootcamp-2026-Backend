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
        dto.setInviteeId(invitation.getInvitee().getId());
        dto.setStatus(invitation.getStatus());
        dto.setInvitedAt(invitation.getInvitedAt());
        dto.setRespondedAt(invitation.getRespondedAt());
        dto.setMeeting(MeetingMapper.convertToDto(invitation.getMeeting()));
        return dto;
    }
}
