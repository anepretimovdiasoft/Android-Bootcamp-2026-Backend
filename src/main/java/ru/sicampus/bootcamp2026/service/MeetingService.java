package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.MeetingDto;

import java.util.List;

public interface MeetingService {

    List<MeetingDto> getAllMeetingss();

    MeetingDto getMeetingById(Long id);

    MeetingDto createMeeting(MeetingDto dto);

    MeetingDto updateMeeting(Long id, MeetingDto dto);

    void deleteMeeting(Long id);
}
