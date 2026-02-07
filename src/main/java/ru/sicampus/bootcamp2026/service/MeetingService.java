package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.MeetingDto;

public interface MeetingService {

    MeetingDto getMeetingById(Long id);

    MeetingDto createMeeting(MeetingDto dto);

    MeetingDto updateMeeting(Long id, MeetingDto dto, Long useId);

    void deleteMeeting(Long id, Long userId);
}
