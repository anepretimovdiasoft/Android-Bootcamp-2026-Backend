package ru.examle.edu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.examle.edu.dto.InvitationDTO;

public interface InvitationService {
    Page<InvitationDTO> getAllInvitations(Pageable pageable);
    InvitationDTO getInvitationById(Long id);
    InvitationDTO createInvitation(InvitationDTO invitationDTO);
    InvitationDTO updateInvitation(Long id, InvitationDTO invitationDTO);
    void deleteInvitation(Long id);
}
