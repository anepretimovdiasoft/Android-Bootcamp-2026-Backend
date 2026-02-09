package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.entity.*;
import ru.sicampus.bootcamp2026.exceptions.*;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UsersMeetingRepository;
import ru.sicampus.bootcamp2026.repository.UsersRepository;
import ru.sicampus.bootcamp2026.service.InvitationService;
import ru.sicampus.bootcamp2026.util.InvitationMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {
    private final InvitationRepository invitationRepository;
    private final UsersMeetingRepository usersMeetingRepository;
    private final UsersRepository usersRepository;
    private final MeetingRepository meetingRepository;

    @Override
    public List<InvitationDTO> getAllInvitation(Users user) {
        return invitationRepository.findAllByTarget(user).stream()
                .map(InvitationMapper::convertToDto)
                .toList();
    }

    @Override
    public void acceptInvitation(Users user, Long id) {
        Invitation invitation = invitationRepository.findById(id).orElseThrow(() -> new InvitationNotExistException("Invitation not exist"));
        if (invitation.getTarget().getId() != user.getId()) {
            throw new InvitationNotSentToYouException("This invitation was sent not for you");
        }
        if (invitation.getStatus() != InvitationStatus.AWAITS) {
            throw new InvitationHasAlreadyRespondedException("The invitation has already been responded to");
        }
        LocalDateTime start = invitation.getMeeting().getStart();
        LocalDateTime end = start.plusMinutes(invitation.getMeeting().getDuration());

        if (!meetingRepository.findOverlappingMeetingIds(start, end, user.getId()).isEmpty()) {
            throw new AlreadyExistMeetingAtThisTimeException("You already have meeting at this time");
        }

        UsersMeeting relation = new UsersMeeting();
        relation.setMember(user);
        relation.setMeeting(invitation.getMeeting());
        relation.setStatus(UsersMeetingStatus.GUEST);

        usersMeetingRepository.save(relation);
        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitationRepository.save(invitation);
    }

    @Override
    public void rejectInvitation(Users user, Long id) {
        Invitation invitation = invitationRepository.findById(id).orElseThrow(() -> new InvitationNotExistException("Invitation not exist"));
        if (invitation.getTarget().getId() != user.getId()) {
            throw new InvitationNotSentToYouException("This invitation was sent not for you");
        }
        if (invitation.getStatus() != InvitationStatus.AWAITS) {
            throw new InvitationHasAlreadyRespondedException("The invitation has already been responded to");
        }
        invitation.setStatus(InvitationStatus.REJECTED);
        invitationRepository.save(invitation);
    }

    @Override
    public InvitationDTO sendInvitation(Users user, Long meetingId, String emailTarget) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new MeetingNotExistException("Meeting not exist"));
        UsersMeeting relation = usersMeetingRepository.findByMemberAndMeeting(user, meeting).orElseThrow(() -> new NotEnoughRightException("You haven't enough right for sent invitation"));
        if (!(relation.getStatus().equals(UsersMeetingStatus.CREATOR) || relation.getStatus().equals(UsersMeetingStatus.MANAGER))) {
            throw new NotEnoughRightException("You haven't enough right for sent invitation");
        }

        Users target = usersRepository.findByEmail(emailTarget).orElseThrow(() -> new UserNotFoundException("Target not found"));

        Optional<UsersMeeting> existingRelation = usersMeetingRepository
                .findByMemberAndMeeting(target, meeting);
        if (existingRelation.isPresent()) {
            throw new UserAlreadyInMeetingException("User is already in this meeting");
        }

        Optional<Invitation> existingInvitation = invitationRepository.findByTargetAndMeetingWithStatuses(
                target,
                meeting,
                InvitationStatus.AWAITS
        );
        if (existingInvitation.isPresent()) {
            throw new InvitationAlreadyExistsException("User already have active invitation on this meeting");
        }

        Invitation invitation = new Invitation();
        invitation.setStatus(InvitationStatus.AWAITS);
        invitation.setTarget(target);
        invitation.setMeeting(meeting);
        invitation.setCreatedAt(LocalDateTime.now());

        return InvitationMapper.convertToDto(invitationRepository.save(invitation));
    }
}
