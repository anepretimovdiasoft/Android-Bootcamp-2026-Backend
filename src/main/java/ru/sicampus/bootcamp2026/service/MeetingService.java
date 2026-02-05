package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.fromApp.NewMeetingDTO;
import ru.sicampus.bootcamp2026.dto.fromApp.MeetingResponseDTO;
import ru.sicampus.bootcamp2026.dto.toApp.MeetingDTO;
import ru.sicampus.bootcamp2026.dto.toApp.TimeSlotDTO;

import java.time.LocalDate;
import java.util.List;

public interface MeetingService {
    MeetingDTO createMeeting(NewMeetingDTO dto);

    void deleteMeeting(Long meetingId);

    MeetingDTO getMeetingById(Long meetingId);
    List<MeetingDTO> getAllMeetings();

    List<TimeSlotDTO> getBookedSlotsByDate(String date);

    List<MeetingDTO> getUserMeetings(
            Long userId,
            String startDate,
            String endDate
    );

    void respondToMeeting(MeetingResponseDTO dto);

    List<MeetingDTO> getUserInvitations(Long id);
}
