package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.InvitationNotFoundException;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.InvitationService;
import ru.sicampus.bootcamp2026.util.InvitationMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    @Override
    public Page<InvitationDTO> getAllInvitations(Pageable pageable) {
        return invitationRepository.findAll(pageable)
                .map(InvitationMapper::convertToDto);
    }

    @Override
    public InvitationDTO getInvitationById(Long id) {
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new InvitationNotFoundException("Invitation not found"));
        return InvitationMapper.convertToDto(invitation);
    }

    @Override
    public InvitationDTO createInvitation(InvitationDTO dto) {
        Meeting meeting = meetingRepository.findById(dto.getMeetingId())
                .orElseThrow(() -> new MeetingNotFoundException("Meeting not found"));
        User invitee = userRepository.findById(dto.getInviteeId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (meeting.getOrganizer().getId() == invitee.getId()) {
            throw new IllegalArgumentException("Организатор не может пригласить сам себя на мероприятие.");
        }

        Invitation invitation = new Invitation();
        invitation.setMeeting(meeting);
        invitation.setInvitee(invitee);
        invitation.setStatus(dto.getStatus());
        invitation.setInvitedAt(LocalDateTime.now());
        invitation.setRespondedAt(dto.getRespondedAt());

        return InvitationMapper.convertToDto(invitationRepository.save(invitation));
    }

    @Override
    public InvitationDTO updateInvitation(Long id, InvitationDTO dto) {
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new InvitationNotFoundException("Invitation not found"));

        Meeting meeting = meetingRepository.findById(dto.getMeetingId())
                .orElseThrow(() -> new MeetingNotFoundException("Meeting not found"));
        User invitee = userRepository.findById(dto.getInviteeId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        invitation.setMeeting(meeting);
        invitation.setInvitee(invitee);
        invitation.setStatus(dto.getStatus());
        invitation.setRespondedAt(dto.getRespondedAt());

        return InvitationMapper.convertToDto(invitationRepository.save(invitation));
    }

    @Override
    public Page<InvitationDTO> getInvitationsByInvitee(Long inviteeId, Pageable pageable) {
        return invitationRepository.findByInviteeId(inviteeId, pageable)
                .map(InvitationMapper::convertToDto);
    }

    @Override
    public void deleteInvitation(Long id) {
        invitationRepository.deleteById(id);
    }
}
