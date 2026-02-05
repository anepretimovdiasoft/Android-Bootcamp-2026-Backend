package ru.sicampus.bootcamp2026.service;


import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.CreateMeetingDto;
import ru.sicampus.bootcamp2026.dto.MeetingsDto;

import java.time.LocalDate;
import java.util.List;


@Service
public interface MeetingService {

    MeetingsDto getMeeting(Long id);

    void createMeeting(CreateMeetingDto request);

    List<MeetingsDto> getMyMeetingsByDay(Long userId, LocalDate date);

    void deleteMeeting(long id);
}