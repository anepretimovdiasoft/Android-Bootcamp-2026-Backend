package com.teto.planner.service;

import com.teto.planner.dto.BusySlotDto;
import com.teto.planner.dto.BusySlotsResponse;
import com.teto.planner.dto.AddParticipantsRequest;
import com.teto.planner.dto.MeetingDto;
import com.teto.planner.dto.MeetingParticipantDto;
import com.teto.planner.dto.MeetingsPage;
import com.teto.planner.dto.PageMeta;
import com.teto.planner.dto.MeetingCreateRequest;
import com.teto.planner.dto.MeetingUpdateRequest;
import com.teto.planner.dto.UpdateParticipantRequest;
import com.teto.planner.entity.MeetingEntity;
import com.teto.planner.entity.MeetingParticipantEntity;
import com.teto.planner.entity.MeetingParticipantId;
import com.teto.planner.entity.MeetingStatus;
import com.teto.planner.entity.ParticipantRole;
import com.teto.planner.entity.ParticipantStatus;
import com.teto.planner.entity.UserEntity;
import com.teto.planner.exception.ConflictException;
import com.teto.planner.exception.ForbiddenException;
import com.teto.planner.exception.NotFoundException;
import com.teto.planner.mapper.MeetingMapper;
import com.teto.planner.pagination.Pagination;
import com.teto.planner.repository.MeetingParticipantRepository;
import com.teto.planner.repository.MeetingRepository;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingParticipantRepository participantRepository;
    private final UserService userService;
    private final RoomService roomService;
    private final MeetingMapper meetingMapper;

    public MeetingService(
            MeetingRepository meetingRepository,
            MeetingParticipantRepository participantRepository,
            UserService userService,
            RoomService roomService,
            MeetingMapper meetingMapper
    ) {
        this.meetingRepository = meetingRepository;
        this.participantRepository = participantRepository;
        this.userService = userService;
        this.roomService = roomService;
        this.meetingMapper = meetingMapper;
    }

    public MeetingsPage listMeetings(UserEntity currentUser, LocalDate startDate, LocalDate endDate,
                                     boolean includePending, int page, int size) {
        Page<MeetingEntity> meetings = meetingRepository.findForUserBetween(
                currentUser.getId(),
                startDate,
                endDate,
                includePending,
                Pagination.pageRequest(page, size, Sort.by(
                        Sort.Order.asc("meetingDate"),
                        Sort.Order.asc("startHour"),
                        Sort.Order.asc("id")
                )));
        List<MeetingDto> items = meetings.getContent().stream()
                .map(meetingMapper::toDto)
                .collect(Collectors.toList());
        return new MeetingsPage(items, new PageMeta(page, size, meetings.getTotalElements()));
    }

    public MeetingDto getMeeting(UUID meetingId) {
        MeetingEntity meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new NotFoundException("MEETING_NOT_FOUND", "Meeting not found"));
        return meetingMapper.toDto(meeting);
    }

    @Transactional
    public MeetingDto createMeeting(UserEntity organizer, MeetingCreateRequest request) {
        int duration = request.durationHours() != null ? request.durationHours() : 1;
        short startHour = request.startHour().shortValue();
        short durationHours = (short) duration;

        if (participantRepository.countAcceptedAtSlot(organizer.getId(), request.meetingDate(), startHour) > 0) {
            throw new ConflictException("SLOT_CONFLICT", "Organizer is busy at that slot");
        }

        if (request.roomId() != null && meetingRepository.existsByRoom_IdAndMeetingDateAndStartHourAndStatus(
                request.roomId(), request.meetingDate(), startHour, MeetingStatus.SCHEDULED)) {
            throw new ConflictException("ROOM_CONFLICT", "Room already booked for that slot");
        }

        MeetingEntity meeting = new MeetingEntity();
        meeting.setId(UUID.randomUUID());
        meeting.setOrganizer(organizer);
        meeting.setTitle(request.title());
        meeting.setDescription(request.description());
        meeting.setMeetingDate(request.meetingDate());
        meeting.setStartHour(startHour);
        meeting.setDurationHours(durationHours);
        meeting.setStatus(MeetingStatus.SCHEDULED);

        if (request.roomId() != null) {
            meeting.setRoom(roomService.findRoom(request.roomId()));
        }

        Set<MeetingParticipantEntity> participants = new HashSet<>();
        participants.add(buildParticipant(meeting, organizer, ParticipantRole.ORGANIZER, ParticipantStatus.ACCEPTED));

        if (request.participantIds() != null) {
            for (UUID userId : request.participantIds()) {
                if (userId.equals(organizer.getId())) {
                    continue;
                }
                UserEntity user = userService.findUser(userId);
                participants.add(buildParticipant(meeting, user, ParticipantRole.ATTENDEE, ParticipantStatus.PENDING));
            }
        }

        meeting.setParticipants(participants);
        MeetingEntity saved = meetingRepository.save(meeting);
        return meetingMapper.toDto(saved);
    }

    @Transactional
    public MeetingDto updateMeeting(UserEntity currentUser, UUID meetingId, MeetingUpdateRequest request) {
        MeetingEntity meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new NotFoundException("MEETING_NOT_FOUND", "Meeting not found"));

        requireOrganizer(currentUser, meeting);

        if (request.title() != null) {
            meeting.setTitle(request.title());
        }
        if (request.description() != null) {
            meeting.setDescription(request.description());
        }
        if (request.roomId() != null) {
            UUID currentRoomId = meeting.getRoom() != null ? meeting.getRoom().getId() : null;
            if (currentRoomId == null || !currentRoomId.equals(request.roomId())) {
                if (meetingRepository.existsByRoom_IdAndMeetingDateAndStartHourAndStatus(
                        request.roomId(), meeting.getMeetingDate(), meeting.getStartHour(), MeetingStatus.SCHEDULED)) {
                    throw new ConflictException("ROOM_CONFLICT", "Room already booked for that slot");
                }
                meeting.setRoom(roomService.findRoom(request.roomId()));
            }
        }
        if (request.status() != null) {
            meeting.setStatus(request.status());
        }

        return meetingMapper.toDto(meeting);
    }

    @Transactional
    public void cancelMeeting(UserEntity currentUser, UUID meetingId) {
        MeetingEntity meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new NotFoundException("MEETING_NOT_FOUND", "Meeting not found"));
        requireOrganizer(currentUser, meeting);
        meeting.setStatus(MeetingStatus.CANCELLED);
    }

    public BusySlotsResponse getBusySlots(UserEntity currentUser, LocalDate meetingDate) {
        List<MeetingParticipantEntity> slots = participantRepository.findBusySlots(currentUser.getId(), meetingDate);
        List<BusySlotDto> items = slots.stream()
                .map(mp -> new BusySlotDto(mp.getStartHour() != null ? mp.getStartHour().intValue() : null, mp.getMeeting().getId()))
                .collect(Collectors.toList());
        return new BusySlotsResponse(meetingDate, items);
    }

    public List<MeetingParticipantDto> listParticipants(UUID meetingId) {
        MeetingEntity meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new NotFoundException("MEETING_NOT_FOUND", "Meeting not found"));
        return meeting.getParticipants().stream().map(meetingMapper::toParticipant).collect(Collectors.toList());
    }

    @Transactional
    public MeetingDto addParticipants(UserEntity currentUser, UUID meetingId, AddParticipantsRequest request) {
        MeetingEntity meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new NotFoundException("MEETING_NOT_FOUND", "Meeting not found"));
        requireOrganizer(currentUser, meeting);

        Set<UUID> existing = meeting.getParticipants().stream()
                .map(mp -> mp.getUser().getId())
                .collect(Collectors.toSet());

        for (UUID userId : request.userIds()) {
            if (existing.contains(userId)) {
                continue;
            }
            UserEntity user = userService.findUser(userId);
            meeting.getParticipants().add(buildParticipant(meeting, user, ParticipantRole.ATTENDEE, ParticipantStatus.PENDING));
        }

        return meetingMapper.toDto(meeting);
    }

    @Transactional
    public MeetingParticipantDto updateParticipant(UserEntity currentUser, UUID meetingId, UUID userId,
                                                   UpdateParticipantRequest request) {
        MeetingParticipantEntity participant = participantRepository.findById(new MeetingParticipantId(meetingId, userId))
                .orElseThrow(() -> new NotFoundException("PARTICIPANT_NOT_FOUND", "Participant not found"));

        MeetingEntity meeting = participant.getMeeting();
        requireOrganizer(currentUser, meeting);

        if (request.status() != null) {
            if (request.status() == ParticipantStatus.ACCEPTED
                    && participant.getStatus() != ParticipantStatus.ACCEPTED) {
                long conflicts = participantRepository.countAcceptedAtSlot(userId, meeting.getMeetingDate(), meeting.getStartHour());
                if (conflicts > 0) {
                    throw new ConflictException("SLOT_CONFLICT", "User already has accepted meeting at that slot");
                }
            }
            participant.setStatus(request.status());
            participant.setRespondedAt(OffsetDateTime.now());
        }
        return meetingMapper.toParticipant(participant);
    }

    @Transactional
    public void deleteParticipant(UserEntity currentUser, UUID meetingId, UUID userId) {
        MeetingEntity meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new NotFoundException("MEETING_NOT_FOUND", "Meeting not found"));
        requireOrganizer(currentUser, meeting);
        participantRepository.deleteById(new MeetingParticipantId(meetingId, userId));
    }

    private void requireOrganizer(UserEntity currentUser, MeetingEntity meeting) {
        if (!meeting.getOrganizer().getId().equals(currentUser.getId())) {
            throw new ForbiddenException("FORBIDDEN", "Only organizer can modify meeting");
        }
    }

    private MeetingParticipantEntity buildParticipant(MeetingEntity meeting, UserEntity user,
                                                      ParticipantRole role, ParticipantStatus status) {
        MeetingParticipantEntity participant = new MeetingParticipantEntity();
        participant.setId(new MeetingParticipantId(meeting.getId(), user.getId()));
        participant.setMeeting(meeting);
        participant.setUser(user);
        participant.setMeetingDate(meeting.getMeetingDate());
        participant.setStartHour(meeting.getStartHour());
        participant.setRole(role);
        participant.setStatus(status);
        return participant;
    }
}
