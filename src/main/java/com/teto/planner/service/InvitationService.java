package com.teto.planner.service;

import com.teto.planner.dto.InvitationDto;
import com.teto.planner.dto.InvitationsResponse;
import com.teto.planner.dto.MeetingParticipantDto;
import com.teto.planner.dto.PageMeta;
import com.teto.planner.dto.InvitationResponseStatus;
import com.teto.planner.entity.MeetingEntity;
import com.teto.planner.entity.MeetingParticipantEntity;
import com.teto.planner.entity.MeetingParticipantId;
import com.teto.planner.entity.ParticipantStatus;
import com.teto.planner.entity.UserEntity;
import com.teto.planner.exception.ConflictException;
import com.teto.planner.exception.NotFoundException;
import com.teto.planner.mapper.MeetingMapper;
import com.teto.planner.repository.MeetingParticipantRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvitationService {
    private final MeetingParticipantRepository participantRepository;
    private final MeetingMapper meetingMapper;

    public InvitationService(MeetingParticipantRepository participantRepository, MeetingMapper meetingMapper) {
        this.participantRepository = participantRepository;
        this.meetingMapper = meetingMapper;
    }

    public InvitationsResponse listInvitations(UserEntity currentUser, ParticipantStatus status, int page, int size) {
        ParticipantStatus effective = status == null ? ParticipantStatus.PENDING : status;
        Page<MeetingParticipantEntity> pageResult = participantRepository.findByUserIdAndStatus(
                currentUser.getId(), effective, PageRequest.of(page, size));

        List<InvitationDto> items = pageResult.getContent().stream()
                .map(mp -> new InvitationDto(
                        meetingMapper.toDto(mp.getMeeting()),
                        mp.getRole(),
                        mp.getStatus()
                ))
                .collect(Collectors.toList());

        return new InvitationsResponse(items, new PageMeta(page, size, pageResult.getTotalElements()));
    }

    @Transactional
    public MeetingParticipantDto respond(UserEntity currentUser, UUID meetingId, InvitationResponseStatus status) {
        MeetingParticipantEntity participant = participantRepository.findById(new MeetingParticipantId(meetingId, currentUser.getId()))
                .orElseThrow(() -> new NotFoundException("INVITATION_NOT_FOUND", "Invitation not found"));

        if (status == InvitationResponseStatus.ACCEPTED
                && participant.getStatus() != ParticipantStatus.ACCEPTED) {
            MeetingEntity meeting = participant.getMeeting();
            long conflicts = participantRepository.countAcceptedAtSlot(currentUser.getId(), meeting.getMeetingDate(), meeting.getStartHour());
            if (conflicts > 0) {
                throw new ConflictException("SLOT_CONFLICT", "You already have accepted meeting at that slot");
            }
            participant.setStatus(ParticipantStatus.ACCEPTED);
            participant.setRespondedAt(OffsetDateTime.now());

            List<MeetingParticipantEntity> pending = participantRepository.findPendingAtSlot(
                    currentUser.getId(), meeting.getMeetingDate(), meeting.getStartHour());
            for (MeetingParticipantEntity other : pending) {
                if (!other.getMeeting().getId().equals(meetingId)) {
                    other.setStatus(ParticipantStatus.DECLINED);
                    other.setRespondedAt(OffsetDateTime.now());
                }
            }
        } else if (status == InvitationResponseStatus.DECLINED) {
            participant.setStatus(ParticipantStatus.DECLINED);
            participant.setRespondedAt(OffsetDateTime.now());
        }

        return meetingMapper.toParticipant(participant);
    }
}
