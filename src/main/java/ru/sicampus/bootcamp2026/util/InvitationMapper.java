package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;

@UtilityClass
public class InvitationMapper {
    public InvitationDTO convertToDto(Invitation invitation) {
        InvitationDTO invitationDTO = new InvitationDTO();
        invitationDTO.setId(invitation.getId());
        invitationDTO.setUserId(invitation.getInvitedUser().getId());
        invitationDTO.setMeetingId(invitation.getMeeting().getId());
        invitationDTO.setAccepted(invitation.isAccepted());
        return invitationDTO;
    }
}
