package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.MeetingDTO;

import java.util.List;

public interface MeetingService {
    List<MeetingDTO> getAllMeetings();
    MeetingDTO getMeetingById(Long id);
    MeetingDTO createMeeting(MeetingDTO meetingCreateDto);
    MeetingDTO updateMeeting(Long id, MeetingDTO meetingUpdateDto);
    List<MeetingDTO> searchMeetingsByTitle(String title);
    void deleteMeeting(Long id);
}
