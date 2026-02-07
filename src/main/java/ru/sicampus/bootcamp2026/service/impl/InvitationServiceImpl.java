package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.InvitationDto;
import ru.sicampus.bootcamp2026.enums.InvitationStatus;
import ru.sicampus.bootcamp2026.exception.invitation.InvitationAccessDeniedException;
import ru.sicampus.bootcamp2026.exception.invitation.InvitationAlreadyRespondedException;
import ru.sicampus.bootcamp2026.exception.invitation.InvitationNotFoundException;
import ru.sicampus.bootcamp2026.exception.invitation.InvitationRespondAfterStartMeetingException;
import ru.sicampus.bootcamp2026.exception.user.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.InvitationService;
import ru.sicampus.bootcamp2026.util.InvitationMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;

    @Override
    public List<InvitationDto> getMyInvitations(Long currentUserId) {
        validateUserExists(currentUserId);

        return invitationRepository.findByInviteeId(currentUserId).stream()
                .filter(invitation -> invitation.getStatus().equals(InvitationStatus.PENDING))
                .filter(invitation -> invitation.getMeeting().getStartAt().isAfter(LocalDateTime.now()))
                .map(InvitationMapper::toDto)
                .toList();
    }

    @Override
    public void respondToInvitation(Long invitationId, InvitationStatus status, Long currentUserId) {
        var invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new InvitationNotFoundException("Invitation not found"));
        if (!invitation.getInvitee().getId().equals(currentUserId)){
            throw new InvitationAccessDeniedException("You can only respond to your own invitations");
        }
        if (invitation.getStatus() != InvitationStatus.PENDING) {
            throw new InvitationAlreadyRespondedException("Invitation has already been responded to");
        }
        if (invitation.getMeeting().getStartAt().isBefore(LocalDateTime.now())){
            throw new InvitationRespondAfterStartMeetingException("You can only respond to your invitations which not already started");
        }
        invitation.setStatus(status);
        invitationRepository.save(invitation);
    }

    @Override
    public List<InvitationDto> getMeetingInvitations(Long meetingId, Long organizerId) {
        validateUserExists(organizerId);

        return invitationRepository.findByMeetingId(meetingId)
                .stream()
                .map(InvitationMapper::toDto)
                .toList();
    }

    private void validateUserExists(Long id){
        if (!userRepository.existsById(id)){
            throw new UserNotFoundException("User not found");
        }
    }
}
