package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.InvitationStatus;
import ru.sicampus.bootcamp2026.exception.InvalidStatusException;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.service.InvitationService;
import ru.sicampus.bootcamp2026.util.InvitationMapper;
import ru.sicampus.bootcamp2026.util.MeetingMapper;
import ru.sicampus.bootcamp2026.util.checkers.InvitationChecker;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;

    @Override
    public List<InvitationDTO> getAllInvitations() {
        return invitationRepository.findAll().stream().map(InvitationMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public InvitationDTO respondToInvitation(Long invitationId, String response) {
        Invitation invitation = InvitationChecker.checkInvitation(invitationRepository, invitationId);

        if ("ACCEPT".equalsIgnoreCase(response)) {
            invitation.setStatus(InvitationStatus.ACCEPTED);
        } else if ("DECLINE".equalsIgnoreCase(response)) {
            invitation.setStatus(InvitationStatus.DECLINED);
        } else {
            throw new InvalidStatusException();
        }
        invitation.setRespondedAt(LocalDateTime.now());

        Invitation updated = invitationRepository.save(invitation);

        return InvitationMapper.convertToDto(updated);
    }

    @Override
    public List<InvitationDTO> getPersonInvitations(Long personId) {
        List<Invitation> invitations = invitationRepository.findByPersonId(personId);
        return invitations.stream()
                .map(InvitationMapper::convertToDto)
                .collect(Collectors.toList());
    }
}