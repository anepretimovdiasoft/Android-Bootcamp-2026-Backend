package ru.sicampus.bootcamp2026.service.impl;

import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.InvitationAnswerDTO;
import ru.sicampus.bootcamp2026.dto.InvitationCreateDTO;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.dto.InvitationMeetingDTO;
import ru.sicampus.bootcamp2026.service.InvitationService;

import java.util.List;

@Service
public class InvitationServiceImpl implements InvitationService {
    @Override
    public InvitationDTO createInvitation(InvitationCreateDTO invitationCreateDTO) {
        return null;
    }

    @Override
    public InvitationDTO answerInvitation(InvitationAnswerDTO invitationAnswerDTO) {
        return null;
    }

    @Override
    public List<InvitationMeetingDTO> getActiveInvitations() {
        return List.of();
    }
}
