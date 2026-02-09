package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.request.MeetingCreateDTO;
import ru.sicampus.bootcamp2026.dto.response.MeetingResponseDTO;
import ru.sicampus.bootcamp2026.exception.MeetingException;

import java.util.List;

public interface MeetingService {
    MeetingResponseDTO createMeeting(MeetingCreateDTO dto) throws MeetingException;
    List<MeetingResponseDTO> getSchedule();
    void deleteMeeting(long id) throws MeetingException;
}
