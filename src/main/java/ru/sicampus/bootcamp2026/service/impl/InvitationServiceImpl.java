package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.aspect.annotation.LogExample;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.InvitationStatus;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exceptions.AccessDeniedException;
import ru.sicampus.bootcamp2026.exceptions.ResourceNotFoundException;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.service.InvitationService;
import ru.sicampus.bootcamp2026.web.dto.invitation.InvitationRespondDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;

    @Override
    @LogExample
    @Transactional(readOnly = true)
    public List<Invitation> userInvitations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return invitationRepository.findAllByUserIdAndStatus(user.getId(), InvitationStatus.PENDING);
    }

    @Override
    @LogExample
    @Transactional
    public Invitation respondToInvitation(InvitationRespondDto invitationRespondDto) {
        Invitation invitation = invitationRepository.findById(invitationRespondDto.getInvitationId())
                .orElseThrow(() -> new ResourceNotFoundException("Приглашение не найдено."));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (invitation.getUser().getId() != user.getId())
            throw new AccessDeniedException("Вы не можете ответить на это приглашение.");

        invitation.setStatus(invitationRespondDto.getStatus());
        invitation.setRespondedAt(LocalDateTime.now());

        return invitationRepository.save(invitation);
    }

}
