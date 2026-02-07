package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.response.InvitationResponse;
import ru.sicampus.bootcamp2026.exception.InvitationNotFoundException;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.model.Meeting;
import ru.sicampus.bootcamp2026.model.MeetingParticipant;
import ru.sicampus.bootcamp2026.model.ParticipantStatus;
import ru.sicampus.bootcamp2026.model.User;
import ru.sicampus.bootcamp2026.repository.MeetingParticipantRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.InvitationService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final MeetingParticipantRepository meetingParticipantRepository;
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    @Override
    public List<InvitationResponse> getUserInvitations(UUID userId) {
        List<MeetingParticipant> invitations = meetingParticipantRepository
                .findByUserIdAndStatus(userId, ParticipantStatus.PENDING);

        return invitations.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InvitationResponse getInvitationById(UUID userId, UUID meetingId) {
        MeetingParticipant participant = meetingParticipantRepository
                .findByMeeting_IdAndUser_Id(meetingId, userId)
                .orElseThrow(() -> new InvitationNotFoundException(
                        "Приглашение на встречу не найдено"));

        if (participant.getStatus() != ParticipantStatus.PENDING) {
            throw new InvitationNotFoundException(
                    "Приглашение уже обработано или не существует");
        }

        return mapToResponse(participant);
    }

    @Override
    public InvitationResponse respondToInvitation(UUID userId, UUID meetingId, ParticipantStatus status) {
        MeetingParticipant participant = meetingParticipantRepository
                .findByMeeting_IdAndUser_Id(meetingId, userId)
                .orElseThrow(() -> new InvitationNotFoundException(
                        "Приглашение не найдено"));

        if (participant.getStatus() != ParticipantStatus.PENDING) {
            throw new IllegalStateException("Приглашение уже обработано");
        }

        participant.setStatus(status);
        meetingParticipantRepository.save(participant);

        return mapToResponse(participant);
    }

    @Override
    public void cancelInvitation(UUID organizerId, UUID meetingId, UUID participantId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new MeetingNotFoundException("Встреча не найдена"));

        if (!meeting.getOrganizer_id().getId().equals(organizerId)) {
            throw new SecurityException("Только организатор может отменить приглашение");
        }

        MeetingParticipant participant = meetingParticipantRepository // Отмена приглашения
                .findByMeeting_IdAndUser_Id(meetingId, participantId)
                .orElseThrow(() -> new InvitationNotFoundException("Участник не найден"));

        meetingParticipantRepository.delete(participant);
    }

    /**
     * Маппинг сущности MeetingParticipant в InvitationResponse
     */
    private InvitationResponse mapToResponse(MeetingParticipant participant) {
        Meeting meeting = participant.getMeeting();
        User organizer = meeting.getOrganizer_id();

        return InvitationResponse.builder()
                .id(participant.getId().getMeetingId())
                .meetingId(meeting.getId())
                .meetingTitle(meeting.getTitle())
                .meetingDescription(meeting.getDescription())
                .meetingLocation(meeting.getLocation())
                .meetingStartTime(meeting.getStartTime())
                .meetingEndTime(meeting.getEndTime())
                .organizerUsername(organizer.getUsername())
                .status(participant.getStatus())
                .createdAt(meeting.getCreatedAt())
                .build();
    }
}