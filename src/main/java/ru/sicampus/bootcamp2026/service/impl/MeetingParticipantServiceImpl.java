package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.MeetingParticipantDTO;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.MeetingParticipant;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.ResourceNotFoundException;
import ru.sicampus.bootcamp2026.repository.MeetingParticipantRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.MeetingParticipantService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingParticipantServiceImpl implements MeetingParticipantService {

    private final MeetingParticipantRepository participantRepository;
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    @Override
    public List<MeetingParticipantDTO> getParticipantsByMeeting(Long meetingId) {
        return participantRepository.findByMeetingId(meetingId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingParticipantDTO> getParticipantsByUser(Long userId) {
        return participantRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MeetingParticipantDTO addParticipant(Long meetingId, MeetingParticipantDTO dto) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with id: " + meetingId));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));

        if (meeting.getOrganizer().getId().equals(dto.getUserId())) {
            throw new RuntimeException("Organizer cannot be added as a participant");
        }

        if (participantRepository.existsByMeetingIdAndUserId(meetingId, dto.getUserId())) {
            throw new RuntimeException("User is already a participant of this meeting");
        }

        MeetingParticipant participant = new MeetingParticipant();
        participant.setMeeting(meeting);
        participant.setUser(user);
        participant.setStatus(dto.getStatus());

        MeetingParticipant savedParticipant = participantRepository.save(participant);
        return convertToDto(savedParticipant);
    }

    @Override
    public void updateParticipantStatus(Long meetingId, Long userId, MeetingParticipantDTO dto) {
        MeetingParticipant participant = participantRepository
                .findByMeetingIdAndUserId(meetingId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Participant not found for meeting " + meetingId + " and user " + userId));

        participant.setStatus(dto.getStatus());
        participantRepository.save(participant);
    }

    @Override
    public void removeParticipant(Long meetingId, Long userId) {
        MeetingParticipant participant = participantRepository
                .findByMeetingIdAndUserId(meetingId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Participant not found for meeting " + meetingId + " and user " + userId));

        participantRepository.delete(participant);
    }

    private MeetingParticipantDTO convertToDto(MeetingParticipant participant) {
        Long meetingId = participant.getMeeting() != null ? participant.getMeeting().getId() : null;
        Long userId = participant.getUser() != null ? participant.getUser().getId() : null;

        return new MeetingParticipantDTO(
                participant.getId(),
                meetingId,
                userId,
                participant.getStatus()
        );
    }
}