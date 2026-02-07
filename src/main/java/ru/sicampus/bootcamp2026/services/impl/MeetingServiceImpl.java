package ru.sicampus.bootcamp2026.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.dtos.MeetingDto;
import ru.sicampus.bootcamp2026.entities.Meeting;
import ru.sicampus.bootcamp2026.entities.User;
import ru.sicampus.bootcamp2026.exeptions.MeetingNotFound;
import ru.sicampus.bootcamp2026.exeptions.UserNotFound;
import ru.sicampus.bootcamp2026.repositories.MeetingRepository;
import ru.sicampus.bootcamp2026.repositories.UserRepository;
import ru.sicampus.bootcamp2026.services.MeetingService;
import ru.sicampus.bootcamp2026.utils.MeetingMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public MeetingDto createMeeting(MeetingDto dto, Long organizerId) {
        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFound("Организатор с id " + organizerId + " не найден"));

        if (dto.getStartedAt().isAfter(dto.getEndAt())) {
            throw new IllegalArgumentException("Время начала не может быть позже времени окончания");
        }

        Meeting meeting = new Meeting();
        meeting.setTitle(dto.getTitle());
        meeting.setDescription(dto.getDescription());
        meeting.setStartedAt(dto.getStartedAt());
        meeting.setEndAt(dto.getEndAt());
        meeting.setOrganizer(organizer);

        Meeting savedMeeting = meetingRepository.save(meeting);
        return MeetingMapper.convertToDto(savedMeeting);
    }

    @Override
    public MeetingDto getMeetingById(Long id) {
        return meetingRepository.findById(id)
                .map(MeetingMapper::convertToDto)
                .orElseThrow(() -> new MeetingNotFound("Встреча с id " + id + " не найдена"));
    }

    @Override
    public List<MeetingDto> getAllMeetingsByOrganizer(Long organizerId) {
        if (!userRepository.existsById(organizerId)) {
            throw new UserNotFound("Пользователь с id " + organizerId + " не найден");
        }
        return meetingRepository.findAllByOrganizerId(organizerId).stream()
                .map(MeetingMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingDto> getAllMeetings() {
        return meetingRepository.findAll().stream()
                .map(MeetingMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MeetingDto updateMeeting(Long id, MeetingDto dto) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotFound("Встреча с id " + id + " не найдена"));

        if (dto.getStartedAt().isAfter(dto.getEndAt())) {
            throw new IllegalArgumentException("Время начала не может быть позже времени окончания");
        }

        meeting.setTitle(dto.getTitle());
        meeting.setDescription(dto.getDescription());
        meeting.setStartedAt(dto.getStartedAt());
        meeting.setEndAt(dto.getEndAt());

        Meeting updatedMeeting = meetingRepository.save(meeting);
        return MeetingMapper.convertToDto(updatedMeeting);
    }

    @Override
    @Transactional
    public void deleteMeeting(Long id) {
        if (!meetingRepository.existsById(id)) {
            throw new MeetingNotFound("Встреча с id " + id + " не найдена");
        }
        meetingRepository.deleteById(id);
    }
}