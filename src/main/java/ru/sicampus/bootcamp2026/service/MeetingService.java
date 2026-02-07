package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.CreateMeetingRequestDTO;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;

import java.util.List;

public interface MeetingService {
    MeetingDTO createMeeting(CreateMeetingRequestDTO request);

    MeetingDTO getMeeting(Long id);

    void deleteMeeting(Long id);

    List<MeetingDTO> getPersonMeetings(Long PersonId);
}