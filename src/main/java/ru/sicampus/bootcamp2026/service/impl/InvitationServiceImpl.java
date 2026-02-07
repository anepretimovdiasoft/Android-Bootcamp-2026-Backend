package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.InvitationStatus;
import ru.sicampus.bootcamp2026.exception.InvitationNotFoundException;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.service.InvitationService;
import ru.sicampus.bootcamp2026.util.InvitationMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;

    @Override
    public InvitationDTO respondToInvitation(Long invitationId, String response) {
        Invitation invitation = invitationRepository
                .findById(invitationId)
                .orElseThrow(InvitationNotFoundException::new);

        if ("ACCEPT".equalsIgnoreCase(response)) {
            invitation.setStatus(InvitationStatus.ACCEPTED);
        } else if ("DECLINE".equalsIgnoreCase(response)) {
            invitation.setStatus(InvitationStatus.DECLINED);
        }
        invitation.setRespondedAt(LocalDateTime.now());

        Invitation updated = invitationRepository.save(invitation);

        return InvitationMapper.convertToDto(updated);
    }

    @Override
    public List<InvitationDTO> getPersonInvitations(Long PersonId) {
        List<Invitation> invitations = invitationRepository.findByPersonId(PersonId);
        return invitations.stream()
                .map(InvitationMapper::convertToDto)
                .collect(Collectors.toList());
    }
}