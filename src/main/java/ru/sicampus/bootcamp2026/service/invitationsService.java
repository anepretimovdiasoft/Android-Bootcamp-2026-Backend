package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.cinvitationsDTO;
import ru.sicampus.bootcamp2026.dto.invitationsDTO;
import java.util.List;

public interface invitationsService {
    invitationsDTO createInvitation(cinvitationsDTO createDTO);
    invitationsDTO getInvitationById(Long id);
    List<invitationsDTO> getAllInvitations();
    List<invitationsDTO> getInvitationsByUserId(Long userId);
    List<invitationsDTO> getInvitationsByMeetId(Long meetId);
    void deleteInvitation(Long id);
    long countInvitationsByMeetId(Long meetId);
}