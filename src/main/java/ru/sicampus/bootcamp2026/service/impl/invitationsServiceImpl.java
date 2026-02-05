package ru.sicampus.bootcamp2026.service.impl;

import ru.sicampus.bootcamp2026.dto.cinvitationsDTO;
import ru.sicampus.bootcamp2026.dto.invitationsDTO;
import ru.sicampus.bootcamp2026.entity.AppUserEntity;
import ru.sicampus.bootcamp2026.entity.invitations;
import ru.sicampus.bootcamp2026.entity.meet;
import ru.sicampus.bootcamp2026.exception.ResourceNotFoundException;
import ru.sicampus.bootcamp2026.exception.DuplicateInvitationException;
import ru.sicampus.bootcamp2026.repository.invitationsRepository;
import ru.sicampus.bootcamp2026.repository.meetRepository;
import ru.sicampus.bootcamp2026.repository.userRepository;
import ru.sicampus.bootcamp2026.service.invitationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class invitationsServiceImpl implements invitationsService {
    private final invitationsRepository invitationsRepository;
    private final meetRepository meetRepository;
    private final userRepository userRepository;

    @Override
    public invitationsDTO createInvitation(cinvitationsDTO createDTO) {
        meet meet = meetRepository.findById(createDTO.getMeetId())
                .orElseThrow(() -> new ResourceNotFoundException("Meet not found with id: " + createDTO.getMeetId()));

        AppUserEntity user = userRepository.findById(createDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + createDTO.getUserId()));

        if (invitationsRepository.existsByMeetIdAndUserId(createDTO.getMeetId(), createDTO.getUserId())) {
            throw new DuplicateInvitationException("Invitation already exists for meet id: " +
                    createDTO.getMeetId() + " and user id: " + createDTO.getUserId());
        }

        invitations invitation = new invitations();
        invitation.setMeet(meet);
        invitation.setUser(user);

        invitations savedInvitation = invitationsRepository.save(invitation);
        return mapToDTO(savedInvitation);
    }

    @Override
    @Transactional(readOnly = true)
    public invitationsDTO getInvitationById(Long id) {
        invitations invitation = invitationsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found with id: " + id));
        return mapToDTO(invitation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<invitationsDTO> getAllInvitations() {
        return invitationsRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<invitationsDTO> getInvitationsByUserId(Long userId) {
        return invitationsRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<invitationsDTO> getInvitationsByMeetId(Long meetId) {
        return invitationsRepository.findByMeetId(meetId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteInvitation(Long id) {
        if (!invitationsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Invitation not found with id: " + id);
        }
        invitationsRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countInvitationsByMeetId(Long meetId) {
        return invitationsRepository.countByMeetId(meetId);
    }

    private invitationsDTO mapToDTO(invitations invitation) {
        invitationsDTO dto = new invitationsDTO();
        dto.setId(invitation.getId());
        dto.setMeetId(invitation.getMeet().getId());
        dto.setUserId(invitation.getUser().getId());
        dto.setMeetTitle(invitation.getMeet().getTitle());
        dto.setUserName(invitation.getUser().getFullName());
        dto.setMeetDate(invitation.getMeet().getMeetDate().toString());
        dto.setMeetTime(invitation.getMeet().getMeetTime().toString());
        return dto;
    }
}