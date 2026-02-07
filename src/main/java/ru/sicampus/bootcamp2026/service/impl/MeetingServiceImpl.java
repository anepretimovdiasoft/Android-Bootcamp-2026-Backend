package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.aspect.annotation.LogExample;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.InvitationStatus;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exceptions.ResourceConflictException;
import ru.sicampus.bootcamp2026.exceptions.ResourceNotFoundException;
import ru.sicampus.bootcamp2026.repository.InvitationRepository;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.web.dto.meeting.MeetingCreateDto;
import ru.sicampus.bootcamp2026.web.dto.meeting.MeetingDto;
import ru.sicampus.bootcamp2026.web.dto.meeting.MeetingMiniDto;
import ru.sicampus.bootcamp2026.web.mappers.InvitationMapper;
import ru.sicampus.bootcamp2026.web.mappers.MeetingMapper;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;
    private final InvitationRepository invitationRepository;

    @Override
    @LogExample
    @Transactional(readOnly = true)
    public MeetingDto getById(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Встреча не найдена."));
        List<Invitation> invitations = invitationRepository.findAllByMeetingId(meeting.getId());

        return MeetingMapper.toDto(meeting, InvitationMapper.toMiniInvitationDtoList(invitations));
    }

    @Override
    @LogExample
    @Transactional
    public MeetingDto create(MeetingCreateDto meetingCreateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (meetingRepository.existsTimeConflict(
                user.getId(),
                meetingCreateDto.getDate(),
                meetingCreateDto.getTimeStart(),
                meetingCreateDto.getTimeEnd())
        ) {
            throw new ResourceConflictException("У вас уже есть встреча в это время.");
        }

        Meeting meeting = Meeting.builder()
                .title(meetingCreateDto.getTitle())
                .address(meetingCreateDto.getAddress())
                .description(meetingCreateDto.getDescription())
                .date(meetingCreateDto.getDate())
                .timeStart(meetingCreateDto.getTimeStart())
                .timeEnd(meetingCreateDto.getTimeEnd())
                .organizer(user)
                .build();

        meetingRepository.save(meeting);

        List<Invitation> invitations = new ArrayList<>();
        for (long userId : meetingCreateDto.getUsersId()) {
            User invitationUser = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден."));

            invitations.add(
                    Invitation.builder()
                            .user(invitationUser)
                            .meeting(meeting)
                            .status(InvitationStatus.PENDING)
                            .build()
            );
        }
        invitationRepository.saveAll(invitations);

        return MeetingMapper.toDto(meeting, InvitationMapper.toMiniInvitationDtoList(invitations));
    }

    @Override
    @LogExample
    @Transactional(readOnly = true)
    public List<Meeting> daySchedule(LocalDate day) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return meetingRepository.daySchedule(user.getId(), day);
    }

    @Override
    @LogExample
    @Transactional(readOnly = true)
    public Map<LocalDate, List<MeetingMiniDto>> weekSchedule(int year, int week) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        LocalDate startDate = getStartOfWeek(year, week);
        LocalDate endDate = startDate.plusDays(6);

        List<Meeting> meetings = meetingRepository.schedule(user.getId(), startDate, endDate);
        List<MeetingMiniDto> meetingsMiniDto = meetings.stream().map(MeetingMapper::toMiniDto).toList();

        Map<LocalDate, List<MeetingMiniDto>> result = new HashMap<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            LocalDate finalDate = date;

            List<MeetingMiniDto> resultMeetings = meetingsMiniDto.stream()
                    .filter(meet -> meet.getDate().isEqual(finalDate))
                    .toList();

            result.put(finalDate, resultMeetings);
        }

        return result;
    }

    @Override
    @LogExample
    @Transactional(readOnly = true)
    public Map<LocalDate, List<MeetingMiniDto>> monthSchedule(int year, int month) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        LocalDate[] monthStartAndEnd = getMonthStartAndEnd(year, month);
        LocalDate startDate = monthStartAndEnd[0];
        LocalDate endDate = monthStartAndEnd[1];

        List<Meeting> meetings = meetingRepository.schedule(user.getId(), startDate, endDate);
        List<MeetingMiniDto> meetingsMiniDto = meetings.stream().map(MeetingMapper::toMiniDto).toList();

        Map<LocalDate, List<MeetingMiniDto>> result = new HashMap<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            LocalDate finalDate = date;

            List<MeetingMiniDto> resultMeetings = meetingsMiniDto.stream()
                    .filter(meet -> meet.getDate().isEqual(finalDate))
                    .toList();

            if (resultMeetings.isEmpty()) continue;

            result.put(finalDate, resultMeetings);
        }

        return result;
    }


    private static LocalDate getStartOfWeek(int year, int weekNumber) {
        return LocalDate.of(year, 1, 1)
                .with(WeekFields.ISO.weekOfWeekBasedYear(), weekNumber)
                .with(WeekFields.ISO.dayOfWeek(), 1);
    }

    public static LocalDate[] getMonthStartAndEnd(int year, int month) {
        LocalDate firstDay = LocalDate.of(year, month, 1);
        LocalDate lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());

        return new LocalDate[]{firstDay, lastDay};
    }

}
