package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.InvitationsDTO;
import ru.sicampus.bootcamp2026.entity.Invitations;
import ru.sicampus.bootcamp2026.entity.Meetings;
import ru.sicampus.bootcamp2026.entity.Users;
import ru.sicampus.bootcamp2026.exception.InvitationNotFoundException;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.InvitationsRepository;
import ru.sicampus.bootcamp2026.repository.MeetingsRepository;
import ru.sicampus.bootcamp2026.repository.UsersRepository;
import ru.sicampus.bootcamp2026.service.InvitationsService;
import ru.sicampus.bootcamp2026.util.InvitationsMapper;

@Service
@RequiredArgsConstructor
public class InvitationsServiceImpl implements InvitationsService {

    private final InvitationsRepository invitationsRepository;
    private final MeetingsRepository meetingsRepository;
    private final UsersRepository usersRepository;

    @Override
    public InvitationsDTO getInvitationById(long id) {
        return invitationsRepository.findById(id).map(InvitationsMapper::convertToDTO).orElseThrow(InvitationNotFoundException::new);
    }

    @Override
    public InvitationsDTO createInvitation(InvitationsDTO invitationsDTO) {
        Users creator = usersRepository.findByName(invitationsDTO.getMeetingCreatorName()).orElseThrow(() -> new UserNotFoundException("Meeting creator not found"));
        Meetings meeting = meetingsRepository.findByCreatorIdAndDate(creator, invitationsDTO.getMeetingDate()).orElseThrow(() -> new MeetingNotFoundException("Meeting not found"));
        Users invited = usersRepository.findByName(invitationsDTO.getInvitedUserName()).orElseThrow(() -> new UserNotFoundException("Invited user not found"));
        Invitations invitation = new Invitations();
        invitation.setMeetingId(meeting);
        invitation.setInvitedUserId(invited);
        invitation.setAccepted(invitationsDTO.isAccepted());
        return InvitationsMapper.convertToDTO(invitationsRepository.save(invitation));
    }

    @Override
    public InvitationsDTO updateInvitation(long id, InvitationsDTO invitationsDTO) {
        Users creator = usersRepository.findByName(invitationsDTO.getMeetingCreatorName()).orElseThrow(() -> new UserNotFoundException("Meeting creator not found"));
        Meetings meeting = meetingsRepository.findByCreatorIdAndDate(creator, invitationsDTO.getMeetingDate()).orElseThrow(() -> new MeetingNotFoundException("Meeting not found"));
        Users invited = usersRepository.findByName(invitationsDTO.getInvitedUserName()).orElseThrow(() -> new UserNotFoundException("Invited user not found"));
        Invitations invitation = invitationsRepository.findById(id).orElseThrow(InvitationNotFoundException::new);
        invitation.setMeetingId(meeting);
        invitation.setInvitedUserId(invited);
        invitation.setAccepted(invitationsDTO.isAccepted());
        return InvitationsMapper.convertToDTO(invitationsRepository.save(invitation));
    }

    @Override
    public void deleteInvitation(long id) {
        Invitations invitation = invitationsRepository.findById(id).orElseThrow(InvitationNotFoundException::new);
        invitationsRepository.delete(invitation);
    }
}