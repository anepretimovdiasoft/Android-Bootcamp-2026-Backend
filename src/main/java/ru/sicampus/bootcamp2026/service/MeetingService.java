package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.web.dto.meeting.MeetingCreateDto;
import ru.sicampus.bootcamp2026.web.dto.meeting.MeetingDto;
import ru.sicampus.bootcamp2026.web.dto.meeting.MeetingMiniDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MeetingService {

    MeetingDto getById(Long id);
    MeetingDto create(MeetingCreateDto meetingCreateDto);

    List<Meeting> daySchedule(LocalDate day);
    Map<LocalDate, List<MeetingMiniDto>> weekSchedule(int year, int week);
    Map<LocalDate, List<MeetingMiniDto>> monthSchedule(int year, int month);

}
