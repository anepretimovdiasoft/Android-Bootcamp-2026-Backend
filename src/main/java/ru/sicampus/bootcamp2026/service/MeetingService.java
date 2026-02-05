package ru.sicampus.bootcamp2026.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;

import java.util.List;

public interface MeetingService {
    MeetingDTO createMeeting(MeetingDTO dto);

    MeetingDTO getMeetingById(Long id);

    List<MeetingDTO> getAllMeetings();

    Page<MeetingDTO> getAllMeetingsPaginated(Pageable pageable);

    List<MeetingDTO> getAllMeetingsByTitle(String title);

    List<MeetingDTO> getAllMeetingsByInvitedUserId(Long id);

    List<MeetingDTO> getAllPlannedMeetingsByUserId(Long id);

    MeetingDTO updateMeeting(Long id, MeetingDTO dto);

    void deleteMeeting(Long id);
}
