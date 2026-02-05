package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.InvitationsDTO;
import ru.sicampus.bootcamp2026.entity.Invitations;

@UtilityClass
public class InvitationsMapper {
    public InvitationsDTO convertToDTO(Invitations invitations) {
        InvitationsDTO invitationsDTO = new InvitationsDTO();
        invitationsDTO.setInvitedUserName(invitations.getInvitedUserId().getName());
        invitationsDTO.setMeetingCreatorName(invitations.getMeetingId().getCreatorId().getName());
        invitationsDTO.setMeetingDate(invitations.getMeetingId().getDate());
        invitationsDTO.setAccepted(invitations.isAccepted());
        return invitationsDTO;
    }
}