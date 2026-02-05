package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import java.util.List;

public interface InvitationService {
    List<InvitationDTO> getAllInvitations();
    InvitationDTO getInvitationById(Long id);
    InvitationDTO createInvitation(InvitationDTO dto);
    InvitationDTO updateInvitation(Long id, InvitationDTO dto);
    void deleteInvitation(Long id);
}
