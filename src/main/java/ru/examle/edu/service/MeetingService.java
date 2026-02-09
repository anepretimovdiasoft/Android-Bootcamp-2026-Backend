package ru.examle.edu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.examle.edu.dto.MeetingDTO;

public interface MeetingService {
    Page<MeetingDTO> getAllMeetings(Pageable pageable);
    MeetingDTO getMeetingById(Long id);
    MeetingDTO createMeeting(MeetingDTO meetingDTO);
    MeetingDTO updateMeeting(Long id, MeetingDTO meetingDTO);
    void deleteMeeting(Long id);
}
