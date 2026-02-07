package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.dto.fromApp.NewMeetingDTO;
import ru.sicampus.bootcamp2026.dto.fromApp.MeetingResponseDTO;
import ru.sicampus.bootcamp2026.dto.toApp.MeetingDTO;
import ru.sicampus.bootcamp2026.dto.toApp.TimeSlotDTO;
import ru.sicampus.bootcamp2026.entity.*;
import ru.sicampus.bootcamp2026.exception.AlreadyBookedTimeSlotException;
import ru.sicampus.bootcamp2026.exception.InvalidTimeRangeException;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.*;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.util.MeetingMapper;
import ru.sicampus.bootcamp2026.util.TimeSlotMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final DateRepository dateRepository;
    private final UserRepository userRepository;
    private final ParticipantStatusRepository participantStatusRepository;


    @Override
    @Transactional
    public MeetingDTO createMeeting(NewMeetingDTO dto) {
        LocalTime startTime = dto.getStartTime();
        LocalTime endTime = dto.getEndTime();
        if (startTime.isAfter(endTime)) {
            throw new InvalidTimeRangeException("Время начала не может быть позднее времени окончания.");
        }

        if (timeSlotRepository.existsAlreadyBookedSlots(dto.getDate(), startTime, endTime)) {
            throw new AlreadyBookedTimeSlotException("Временной слот (" + startTime + " - " + endTime + ") невозможно создать. Часть времени забронирована для другого слота.");
        }

        User organizer = userRepository.findById(dto.getOrganizerId()).orElseThrow(() ->
                new UserNotFoundException("Организатор (id: " + dto.getOrganizerId() + ") не найден."));

        //дата (поиск или добавл нолвой)
        DateTime date = dateRepository.findByDate(dto.getDate()).orElseGet(() -> {
            DateTime dateTime = new DateTime();
            dateTime.setDate(dto.getDate());
            return dateRepository.save(dateTime);
        });

        //создан времен слота
        TimeSlot slot = new TimeSlot();
        slot.setDate(date);
        slot.setStartTime(dto.getStartTime());
        slot.setEndTime(dto.getEndTime());

        //создание меро (включает слот)
        Meeting meeting = new Meeting();
        meeting.setOrganizer(organizer);
        meeting.setTitle(dto.getTitle());
        meeting.setDescription(dto.getDescription());
        meeting.setTimeSlot(slot);

        //поик участн встречи
        List<Long> participantsIds = dto.getParticipantsId();
        List<User> participants = userRepository.findAllByIds(participantsIds);
        if (participants.size() != participantsIds.size()) {
            Set<Long> found = participants.stream()
                    .map(User::getId)
                    .collect(Collectors.toSet());
            List<Long> missed = participantsIds.stream()
                    .filter(id -> !found.contains(id))
                    .toList();

            throw new UserNotFoundException("Пользователи (ID: " + missed + ")не найдены.");
        }

        //доавлен участн
        Long waitingStatusCode = ParticipantStatusEnum.WAITING_FOR_ANSWER.getCode();
        ParticipantStatus waitingStatus = participantStatusRepository.getReferenceById(waitingStatusCode);
        for (User user: participants) {
            InvitedParticipant newParticipant = new InvitedParticipant();
            newParticipant.setParticipant(user);
            newParticipant.setMeeting(meeting);
            newParticipant.setParticipantStatus(waitingStatus);

            meeting.addParticipant(newParticipant);
        }

        return MeetingMapper.convertToDto(meetingRepository.save(meeting));
    }

    @Override
    @Transactional
    public void deleteMeeting(Long id) {
        Meeting meeting = meetingRepository.findById(id).orElseThrow(() ->
                new MeetingNotFoundException("Встреча (id: " + id + ") не найдена."));

        meetingRepository.delete(meeting);
    }

    @Override
    @Transactional(readOnly = true)
    public MeetingDTO getMeetingById(Long id) {
        Meeting meeting = meetingRepository.findById(id).orElseThrow(() ->
                new MeetingNotFoundException("Встреча (id: " + id + ") не найдена."));

        return MeetingMapper.convertToDto(meeting);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeetingDTO> getAllMeetings() {
        return convertList(meetingRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeSlotDTO> getEmptySlotsByDate(String dateTime) {
        LocalDate date = LocalDate.parse(dateTime);
        List<TimeSlot> bookedSlots = timeSlotRepository.getBookedSlotsByDate(date);

        List<TimeSlotDTO> emptySlots = new ArrayList<>();
        LocalTime startWorkTime = LocalTime.of(9, 0);
        LocalTime endWorkTime = LocalTime.of(18,0);

        for (LocalTime start = startWorkTime; start.isBefore(endWorkTime); start = start.plusHours(1)) {
            LocalTime startTime = start;
            LocalTime endTime = start.plusHours(1);

            boolean isBooked = bookedSlots.stream()
                    .anyMatch(booked -> startTime.isBefore(booked.getEndTime()) && endTime.isAfter(booked.getStartTime()));

            if (!isBooked) {
                emptySlots.add(TimeSlotMapper.convertToDto(date, startTime, endTime));
            }
        }
        return emptySlots;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeetingDTO> getUserMeetings(
            Long id,
            String start,
            String end,
            Sort sort
    ) {
        LocalDate startDate = start != null ? LocalDate.parse(start): null;
        LocalDate endDate = end != null ? LocalDate.parse(end): null;
        List<Meeting> meetings = meetingRepository.findUserMeetings(id, startDate, endDate, sort);

        return convertList(meetings);
    }

    @Override
    @Transactional
    public void respondToMeeting(MeetingResponseDTO dto) {
        Meeting meeting = meetingRepository.findById(dto.getMeetingId())
                .orElseThrow(() -> new MeetingNotFoundException("Встреча (id: " + dto.getMeetingId() + ") не найдена."));

        InvitedParticipant participant = meeting.getParticipants()
                .stream()
                .filter(p -> p.getParticipant().getId().equals(dto.getUserId()))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Пользователь (id: " + dto.getUserId() + ") не найден. Не может быть добавлен в встречу."));

        Long statusCode;
        if (dto.getResponse() == false) {
            statusCode = ParticipantStatusEnum.REJECTED.getCode();
        } else {
            statusCode = ParticipantStatusEnum.ACCEPTED.getCode();
        }
        ParticipantStatus newStatus = participantStatusRepository.getStatusById(statusCode);
        participant.setParticipantStatus(newStatus);

    }

    @Override
    @Transactional(readOnly = true)
    public List<MeetingDTO> getUserInvitations(Long id) {
        return convertList(meetingRepository.findUserInvitations(id));
    }

    private List<MeetingDTO> convertList(List<Meeting> meetings) {
        return meetings.stream()
                .map(MeetingMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
