package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;

@UtilityClass
public class InvitationMapper {
    public InvitationDTO convertToDTO(Invitation invitation) {
        InvitationDTO dto = new InvitationDTO();
        dto.setId(invitation.getId());
        dto.setStatus(invitation.getStatus());
        dto.setMessage(invitation.getMessage());
        return dto;
    }
}
