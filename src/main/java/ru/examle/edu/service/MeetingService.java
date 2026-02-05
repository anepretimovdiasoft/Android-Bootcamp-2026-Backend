package ru.examle.edu.service;

import ru.examle.edu.dto.MeetingDTO;
import java.util.List;

public interface MeetingService {
    List<MeetingDTO> getAllMeetings();
    MeetingDTO getMeetingById(Long id);
    MeetingDTO createMeeting(MeetingDTO meetingDTO);
    MeetingDTO updateMeeting(Long id, MeetingDTO meetingDTO);
    void deleteMeeting(Long id);
}
