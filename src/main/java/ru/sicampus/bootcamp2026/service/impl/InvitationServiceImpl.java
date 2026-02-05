package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.InvitationDTO;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.exception.InvitationNotFoundException;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.InvitationService;
import ru.sicampus.bootcamp2026.util.InvitationMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;
    private final InvitationRepository invitationRepository;

    @Override
    public InvitationDTO createInvitation(InvitationDTO dto) {
        Invitation invitation = new Invitation();

        invitation.setInvitedUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found")));
        invitation.setMeeting(meetingRepository.findById(dto.getMeetingId()).orElseThrow(() -> new MeetingNotFoundException("Meeting not found")));
        invitation.setAccepted(false);

        return InvitationMapper.convertToDto(invitationRepository.save(invitation));
    }

    @Override
    public InvitationDTO getInvitationById(Long id) {
        return invitationRepository.findById(id)
                .map(InvitationMapper::convertToDto)
                .orElseThrow(() -> new InvitationNotFoundException("Invitation not found"));
    }

    @Override
    public List<InvitationDTO> getAllInvitations() {
        return invitationRepository.findAll().stream()
                .map(InvitationMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<InvitationDTO> getAllInvitationsPaginated(Pageable pageable) {
        return invitationRepository.findAll(pageable).map(InvitationMapper::convertToDto);
    }

    @Override
    public InvitationDTO updateInvitation(Long id, InvitationDTO dto) {
        Invitation invitation = invitationRepository.findById(id).orElseThrow(() -> new InvitationNotFoundException("Invitation not found"));

        invitation.setInvitedUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found")));
        invitation.setMeeting(meetingRepository.findById(dto.getMeetingId()).orElseThrow(() -> new MeetingNotFoundException("Meeting not found")));
        invitation.setAccepted(dto.isAccepted());

        return InvitationMapper.convertToDto(invitationRepository.save(invitation));
    }

    @Override
    public void deleteInvitation(Long id) {
        invitationRepository.deleteById(id);
    }
}
