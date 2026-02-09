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

    Page<MeetingDTO> getAllMeetingsByInvitedUserIdPaginated(Long id, Pageable pageable);

    Page<MeetingDTO> getAllMeetingsByInvitedUserIdAndDatePaginated(Long id, String dateString, Pageable pageable);

    List<MeetingDTO> getAllPlannedMeetingsByUserId(Long id);

    Page<MeetingDTO> getAllPlannedMeetingsByUserIdPaginated(Long id, Pageable pageable);

    Page<MeetingDTO> getAllPlannedMeetingsByUserIdAndDatePaginated(Long id, String dateString, Pageable pageable);

    Page<MeetingDTO> getAllPlannedMeetingsByUserIdAndDatePeriodPaginated(Long id, String datePeriodStart, String datePeriodEnd, Pageable pageable);

    MeetingDTO updateMeeting(Long id, MeetingDTO dto);

    void deleteMeeting(Long id);
}
