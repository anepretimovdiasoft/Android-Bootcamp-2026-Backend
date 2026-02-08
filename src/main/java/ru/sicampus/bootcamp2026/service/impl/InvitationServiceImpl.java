package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.request.InvitationCreateDTO;
import ru.sicampus.bootcamp2026.dto.request.InvitationAnswerDTO;
import ru.sicampus.bootcamp2026.dto.response.InvitationResponseDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.InvitationStatus;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.InvitationException;
import ru.sicampus.bootcamp2026.exception.MeetingException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.mapper.InvitationMapper;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.InvitationService;
import ru.sicampus.bootcamp2026.util.SecurityUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {
    private final InvitationRepository invitationRepository;
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    @Override
    public List<InvitationResponseDTO> getPendingInvitations(User user) {
        return invitationRepository.findByUserAndStatus(user, InvitationStatus.PENDING)
                .stream().map(InvitationMapper::convertToDto).toList();
    }

    @Override
    public ru.sicampus.bootcamp2026.dto.response.InvitationResponseDTO createInvitation(InvitationCreateDTO dto) throws InvitationException, MeetingException, UserNotFoundException {
        User inviter = SecurityUtils.getCurrentUser();
        User invitee = userRepository.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException(dto.getUserId()));

        Meeting meeting = meetingRepository.findById(dto.getMeetingId())
                .orElseThrow(MeetingException::notFound);

        if (meeting.getOrganizer().getId() != inviter.getId()) {
            throw MeetingException.accessDenied();
        }

        if (invitationRepository.existsByUserAndMeeting(invitee, meeting)) {
            throw InvitationException.exists();
        }

        Invitation invitation = new Invitation(meeting, invitee);

        return InvitationMapper.convertToDto(invitationRepository.save(invitation));
    }

    @Override
    public void replyToInvitation(long id, InvitationAnswerDTO dto) throws InvitationException {
        User user = SecurityUtils.getCurrentUser();

        var invitation = invitationRepository.findById(id)
                .orElseThrow(InvitationException::notFound);

        if (invitation.getUser().getId() != user.getId()) {
            throw InvitationException.accessDenied();
        }

        if (invitation.getStatus() != InvitationStatus.PENDING) {
            throw InvitationException.alreadyResponded();
        }

        invitation.setStatus(dto.isAccepted() ? InvitationStatus.ACCEPTED : InvitationStatus.DECLINED);
        invitationRepository.save(invitation);
    }

    @Override
    public void deleteInvitation(long id) throws InvitationException {
        User currentUser = SecurityUtils.getCurrentUser();

        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(InvitationException::notFound);

        boolean isOwner = invitation.getUser().getId() == currentUser.getId();
        boolean isOrganizer = invitation.getMeeting()
                .getOrganizer()
                .getId() == currentUser.getId();

        if (!isOwner && !isOrganizer) {
            throw InvitationException.accessDenied();
        }

        invitationRepository.delete(invitation);
    }

}
