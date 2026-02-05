package ru.examle.edu.service;

import ru.examle.edu.dto.InvitationDTO;
import java.util.List;

public interface InvitationService {
    List<InvitationDTO> getAllInvitations();
    InvitationDTO getInvitationById(Long id);
    InvitationDTO createInvitation(InvitationDTO invitationDTO);
    InvitationDTO updateInvitation(Long id, InvitationDTO invitationDTO);
    void deleteInvitation(Long id);
}
