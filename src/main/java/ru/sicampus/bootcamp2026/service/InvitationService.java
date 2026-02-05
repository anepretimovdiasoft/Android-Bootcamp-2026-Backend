package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.request.InvitationCreateDTO;
import ru.sicampus.bootcamp2026.dto.request.InvitationAnswerDTO;
import ru.sicampus.bootcamp2026.dto.response.InvitationResponseDTO;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.InvitationException;
import ru.sicampus.bootcamp2026.exception.MeetingException;

import java.util.List;

public interface InvitationService {
    List<InvitationResponseDTO> getPendingInvitations(User user);
    ru.sicampus.bootcamp2026.dto.response.InvitationResponseDTO createInvitation(InvitationCreateDTO dto) throws InvitationException, MeetingException;
    void replyToInvitation(InvitationAnswerDTO dto) throws InvitationException;
    void deleteInvitation(long id) throws InvitationException;
}
