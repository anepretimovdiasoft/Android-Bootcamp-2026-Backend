package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import java.util.List;

public interface MeetingService {
    List<MeetingDTO> getAllMeetings();
    MeetingDTO getMeetingById(Long id);
    MeetingDTO createMeeting(MeetingDTO dto);
    MeetingDTO updateMeeting(Long id, MeetingDTO dto);
    void deleteMeeting(Long id);
}
