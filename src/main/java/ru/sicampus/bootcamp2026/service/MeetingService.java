package ru.sicampus.bootcamp2026.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;

public interface MeetingService {
    Page<MeetingDTO> getAllMeetings(Pageable pageable);
    MeetingDTO getMeetingById(Long id);
    MeetingDTO createMeeting(MeetingDTO dto);
    MeetingDTO updateMeeting(Long id, MeetingDTO dto);
    Page<MeetingDTO> getMeetingsByOrganizer(Long organizerId, Pageable pageable);
    void deleteMeeting(Long id);
}
