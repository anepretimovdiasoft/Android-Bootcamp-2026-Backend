package ru.sicampus.bootcamp2026.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;

import java.util.Date;
import java.util.List;

public interface InvitationService {
    InvitationDTO createInvitation(InvitationDTO dto);

    InvitationDTO getInvitationById(Long id);

    List<InvitationDTO> getAllInvitations();

    Page<InvitationDTO> getAllInvitationsPaginated(Pageable pageable);

    Page<InvitationDTO> getAllUnacceptedInvitationsByUserIdPaginated(Long id, Pageable pageable);

    List<InvitationDTO> getAllInvitationsByUserId(Long id, String since);

    Page<InvitationDTO> getAllInvitationsByUserIdPaginated(Long id, String since, Pageable pageable);

    InvitationDTO updateInvitation(Long id, InvitationDTO dto);

    InvitationDTO confirmInvitationById(Long id);

    void deleteInvitation(Long id);
}
