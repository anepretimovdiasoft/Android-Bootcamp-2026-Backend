package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.MeetingsDTO;
import ru.sicampus.bootcamp2026.dto.UsersDTO;

public interface MeetingsService {
    MeetingsDTO getMeetingById(long id);

    MeetingsDTO createMeeting(MeetingsDTO dto);

    MeetingsDTO updateUser(long id, MeetingsDTO dto);

    void deleteUser(long id);
}