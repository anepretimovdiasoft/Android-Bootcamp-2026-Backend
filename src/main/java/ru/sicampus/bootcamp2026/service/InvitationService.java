package ru.sicampus.bootcamp2026.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.Meet;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.ResourceNotFoundException;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final MeetRepository meetRepository;
    private final UserRepository userRepository;

    public Page<Invitation> getAllInvitations(Pageable pageable) {
        return invitationRepository.findAll(pageable);
    }

    public Invitation getInvitationById(Long id) {
        return invitationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation not found with id: " + id));
    }

    public Page<Invitation> getUserInvitations(Long userId, Pageable pageable) {
        return invitationRepository.findByUserId(userId, pageable);
    }

    public Page<Invitation> getUserInvitationsByStatus(Long userId, String status, Pageable pageable) {
        return invitationRepository.findByUserIdAndStatus(userId, status, pageable);
    }

    public Page<Invitation> getPendingInvitations(Long userId, Pageable pageable) {
        return invitationRepository.findPendingByUserId(userId, pageable);
    }

    public Invitation createInvitation(Long meetId, Long userId) {
        Meet meet = meetRepository.findById(meetId)
                .orElseThrow(() -> new ResourceNotFoundException("Meet not found: " + meetId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        if (invitationRepository.findByMeetIdAndUserId(meetId, userId).isPresent()) {
            throw new IllegalArgumentException("User already invited to this meeting");
        }

        Invitation invitation = new Invitation();
        invitation.setMeet(meet);
        invitation.setUser(user);
        invitation.setStatus("PENDING");

        return invitationRepository.save(invitation);
    }

    public Invitation respondToInvitation(Long invitationId, String response) {
        Invitation invitation = getInvitationById(invitationId);

        if (!response.equals("ACCEPTED") && !response.equals("DECLINED")) {
            throw new IllegalArgumentException("Response must be ACCEPTED or DECLINED");
        }

        invitation.setStatus(response);
        invitation.setRespondedAt(LocalDateTime.now());

        return invitationRepository.save(invitation);
    }

    public void deleteInvitation(Long id) {
        Invitation invitation = getInvitationById(id);
        invitationRepository.delete(invitation);
    }
}