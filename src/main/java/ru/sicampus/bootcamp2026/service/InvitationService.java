package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvitationService {
    Page<InvitationDTO> getAllInvitations(Pageable pageable);
    InvitationDTO getInvitationById(Long id);
    InvitationDTO createInvitation(InvitationDTO dto);
    InvitationDTO updateInvitation(Long id, InvitationDTO dto);
    Page<InvitationDTO> getInvitationsByInvitee(Long inviteeId, Pageable pageable);
    void deleteInvitation(Long id);
}