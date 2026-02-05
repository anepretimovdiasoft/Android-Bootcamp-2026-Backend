package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.MeetingsDTO;

import java.util.List;

public interface MeetingsService {
    List<MeetingsDTO> getAllMeetings();

    MeetingsDTO getMeetingsById(Long id);

    MeetingsDTO createMeetings(MeetingsDTO dto);

    MeetingsDTO updateMeetings(Long id, MeetingsDTO dto);

    void deleteMeetings(Long id);
}
