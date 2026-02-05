package ru.sicampus.bootcamp2026.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.dto.*;
import ru.sicampus.bootcamp2026.entity.*;
import ru.sicampus.bootcamp2026.util.MeetingMapper;
import ru.sicampus.bootcamp2026.repository.*;
import ru.sicampus.bootcamp2026.service.MeetingService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final MeetingStatusRepository statusRepo;
    private final MeetingTypeRepository typeRepo;
    private final InvitationStatusRepository invStatusRepo;
    private final MeetingMapper meetingMapper;

    @Transactional
    @Override
    public void createMeeting(Long organizerId, MeetingCreateDTO dto) {
        if (dto.getDateTime().getMinute() != 0) throw new RuntimeException("Только начало часа");

        User org = userRepository.findById(organizerId).orElseThrow(() -> new RuntimeException("Орг не найден"));
        Meeting m = new Meeting();
        m.setTopic(dto.getTopic());
        m.setCalendarDate(dto.getDateTime().toLocalDate());
        m.setStartTime(dto.getDateTime().toLocalTime());
        m.setEndTime(m.getStartTime().plusHours(1));
        m.setOrganizer(org);
        m.setStatus(statusRepo.findByStatusName("SCHEDULED").orElseThrow());
        m.setType(typeRepo.findByTypeName("ONLINE").orElse(null));

        Meeting saved = meetingRepository.save(m);
        InvitationStatus pending = invStatusRepo.findByStatusName("PENDING").orElseThrow();

        for (Long pId : dto.getParticipantIds()) {
            MeetingParticipant mp = new MeetingParticipant();
            mp.setMeeting(saved);
            mp.setUser(userRepository.findById(pId).orElseThrow());
            mp.setInvitationStatus(pending);
            participantRepository.save(mp);
        }

        MeetingParticipant mpOrg = new MeetingParticipant();
        mpOrg.setMeeting(saved);
        mpOrg.setUser(org);
        mpOrg.setInvitationStatus(invStatusRepo.findByStatusName("ACCEPTED").orElseThrow());
        participantRepository.save(mpOrg);
    }

    @Override
    public MeetingInfoDTO getMeetingInfo(Long meetingId) {
        Meeting m = meetingRepository.findById(meetingId).orElseThrow(() -> new RuntimeException("Встреча не найдена"));
        return meetingMapper.toInfoDTO(m, participantRepository.findByMeetingId(meetingId));
    }

    @Override
    public List<InvitationDTO> getInvitations(Long userId) {
        return participantRepository.findByUserIdAndInvitationStatus_StatusName(userId, "PENDING").stream()
                .map(p -> {
                    InvitationDTO dto = new InvitationDTO();
                    dto.setInvitationId(p.getMeeting().getId().toString());
                    dto.setTopic(p.getMeeting().getTopic());
                    dto.setDateTime(LocalDateTime.of(p.getMeeting().getCalendarDate(), p.getMeeting().getStartTime()));
                    dto.setOrganizerName(p.getMeeting().getOrganizer().getFullName());
                    return dto;
                }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void respondToInvitation(Long userId, Long invitationId, String status) {
        MeetingParticipant p = participantRepository.findByMeetingIdAndUserId(invitationId, userId)
                .orElseThrow(() -> new RuntimeException("Приглашение не найдено"));

        if ("ACCEPTED".equals(status)) {
            Meeting m = p.getMeeting();
            if (!meetingRepository.findConflictingMeetings(userId, m.getCalendarDate(), m.getStartTime(), m.getEndTime()).isEmpty()) {
                throw new RuntimeException("Слот занят");
            }
        }
        p.setInvitationStatus(invStatusRepo.findByStatusName(status).orElseThrow());
        participantRepository.save(p);
    }

    @Override
    public List<ScheduleEntryDTO> getSchedule(Long userId, LocalDate start, LocalDate end) {
        return meetingRepository.findConfirmedMeetings(userId, start, end).stream()
                .map(m -> {
                    ScheduleEntryDTO dto = new ScheduleEntryDTO();
                    dto.setTopic(m.getTopic());
                    dto.setDateTime(LocalDateTime.of(m.getCalendarDate(), m.getStartTime()));
                    return dto;
                }).collect(Collectors.toList());
    }
}