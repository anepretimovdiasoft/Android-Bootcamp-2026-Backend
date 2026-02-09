package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.entity.Users;

import java.util.List;

public interface InvitationService {
    List<InvitationDTO> getAllInvitation(Users user);
    void acceptInvitation(Users user, Long id);
    void rejectInvitation(Users user, Long id);
    InvitationDTO sendInvitation(Users user, Long meetingId, String emailTarget);
}
