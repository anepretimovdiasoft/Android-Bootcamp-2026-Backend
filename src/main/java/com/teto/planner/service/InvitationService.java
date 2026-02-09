package com.teto.planner.service;

import com.teto.planner.dto.InvitationDto;
import com.teto.planner.dto.InvitationsResponse;
import com.teto.planner.dto.LoadStatus;
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
import com.teto.planner.pagination.Pagination;
import com.teto.planner.repository.MeetingParticipantRepository;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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

    @Transactional(readOnly = true)
    public InvitationsResponse listInvitations(UserEntity currentUser, ParticipantStatus status, int page, int size) {
        ParticipantStatus effective = status == null ? ParticipantStatus.PENDING : status;
        Page<MeetingParticipantEntity> pageResult = participantRepository.findByUserIdAndStatus(
                currentUser.getId(),
                effective,
                Pagination.pageRequest(page, size, Sort.by(
                        Sort.Order.asc("meetingDate"),
                        Sort.Order.asc("startHour")
                )));

        Map<LocalDate, Set<UUID>> idsByDate = collectIdsByDate(pageResult.getContent());
        Map<LocalDate, Map<UUID, Integer>> busyByDate = loadBusyHours(idsByDate);
        Map<LocalDate, Map<UUID, LoadStatus>> statusByDate = loadStatusByDate(busyByDate);

        List<InvitationDto> items = pageResult.getContent().stream()
                .map(mp -> {
                    MeetingEntity meeting = mp.getMeeting();
                    Map<UUID, Integer> busy = busyByDate.getOrDefault(meeting.getMeetingDate(), Map.of());
                    Map<UUID, LoadStatus> statusMap = statusByDate.getOrDefault(meeting.getMeetingDate(), Map.of());
                    return new InvitationDto(
                            meetingMapper.toDto(meeting, busy, statusMap),
                            mp.getRole(),
                            mp.getStatus()
                    );
                })
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
            if (participant.getStatus() == ParticipantStatus.DECLINED) {
                ensureRoomCapacity(meeting, 1);
            }
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

    private void ensureRoomCapacity(MeetingEntity meeting, int additionalParticipants) {
        if (meeting.getRoom() == null || meeting.getRoom().getCapacity() == null) {
            return;
        }
        int current = (int) meeting.getParticipants().stream()
                .filter(mp -> mp.getStatus() == ParticipantStatus.PENDING || mp.getStatus() == ParticipantStatus.ACCEPTED)
                .count();
        if (current + additionalParticipants > meeting.getRoom().getCapacity()) {
            throw new ConflictException("ROOM_CAPACITY_EXCEEDED", "Room capacity exceeded");
        }
    }

    private Map<LocalDate, Set<UUID>> collectIdsByDate(List<MeetingParticipantEntity> participants) {
        Map<LocalDate, Set<UUID>> idsByDate = new HashMap<>();
        for (MeetingParticipantEntity participant : participants) {
            MeetingEntity meeting = participant.getMeeting();
            LocalDate date = meeting.getMeetingDate();
            Set<UUID> ids = idsByDate.computeIfAbsent(date, key -> new HashSet<>());
            ids.add(meeting.getOrganizer().getId());
            if (meeting.getParticipants() != null) {
                for (MeetingParticipantEntity mp : meeting.getParticipants()) {
                    ids.add(mp.getUser().getId());
                }
            }
        }
        return idsByDate;
    }

    private Map<LocalDate, Map<UUID, Integer>> loadBusyHours(Map<LocalDate, Set<UUID>> idsByDate) {
        Map<LocalDate, Map<UUID, Integer>> busyByDate = new HashMap<>();
        for (Map.Entry<LocalDate, Set<UUID>> entry : idsByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<UUID> ids = new ArrayList<>(entry.getValue());
            if (ids.isEmpty()) {
                continue;
            }
            Map<UUID, Integer> busy = new HashMap<>();
            for (var row : participantRepository.sumBusyHours(ids, date)) {
                busy.put(row.getUserId(), row.getHours().intValue());
            }
            busyByDate.put(date, busy);
        }
        return busyByDate;
    }

    private Map<LocalDate, Map<UUID, LoadStatus>> loadStatusByDate(Map<LocalDate, Map<UUID, Integer>> busyByDate) {
        Map<LocalDate, Map<UUID, LoadStatus>> statusByDate = new HashMap<>();
        for (Map.Entry<LocalDate, Map<UUID, Integer>> entry : busyByDate.entrySet()) {
            Map<UUID, LoadStatus> status = new HashMap<>();
            for (Map.Entry<UUID, Integer> hours : entry.getValue().entrySet()) {
                status.put(hours.getKey(), toLoadStatus(hours.getValue()));
            }
            statusByDate.put(entry.getKey(), status);
        }
        return statusByDate;
    }

    private LoadStatus toLoadStatus(Integer hours) {
        if (hours == null) {
            return LoadStatus.LOW;
        }
        if (hours <= 2) {
            return LoadStatus.LOW;
        }
        if (hours <= 5) {
            return LoadStatus.MEDIUM;
        }
        return LoadStatus.HIGH;
    }
}
