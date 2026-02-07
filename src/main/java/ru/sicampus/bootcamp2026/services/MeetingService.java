package ru.sicampus.bootcamp2026.services;

import ru.sicampus.bootcamp2026.dtos.MeetingDto;

import java.util.List;

public interface MeetingService {
    MeetingDto createMeeting(MeetingDto meetingDto, Long organizerId);
    MeetingDto getMeetingById(Long id);
    List<MeetingDto> getAllMeetingsByOrganizer(Long organizerId);

    List<MeetingDto> getAllMeetings();

    MeetingDto updateMeeting(Long id, MeetingDto dto);
    void deleteMeeting(Long id);
}
