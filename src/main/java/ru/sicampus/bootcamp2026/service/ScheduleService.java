package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.MeetingDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    List<MeetingDto> getDaySchedule(Long userId, LocalDate date);
    List<MeetingDto> getWeekSchedule(Long userId);
    List<MeetingDto> getTwoWeeksSchedule(Long userId);
    List<MeetingDto> getMonthSchedule(Long userId);
}
