package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.InvitationEmployeeDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;

@UtilityClass
public class InvitationEmployeeMapper {
    public InvitationEmployeeDTO convertToDTO(Invitation invitation) {
        InvitationEmployeeDTO invitationEmployeeDTO = new InvitationEmployeeDTO();

        invitationEmployeeDTO.setId(invitation.getId());
        invitationEmployeeDTO.setStatus(invitation.getStatus());
        invitationEmployeeDTO.setEmployee(EmployeeMapper.convertToDTO(invitation.getEmployee()));

        return invitationEmployeeDTO;
    }
}
