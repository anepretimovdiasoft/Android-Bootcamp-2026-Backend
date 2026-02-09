package ru.sicampus.bootcamp2026.service;

import org.springframework.data.domain.Page;
import ru.sicampus.bootcamp2026.dto.MeetingsDTO;

public interface MeetingsService {
    MeetingsDTO getMeetingById(long id);

    MeetingsDTO createMeeting(MeetingsDTO dto);

    MeetingsDTO updateUser(long id, MeetingsDTO dto);

    void deleteUser(long id);

    Page<MeetingsDTO> getAllPaginated(int page, int size);

    Page<MeetingsDTO> getAllUserInvitedPaginated(String username, int page, int size);
}