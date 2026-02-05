package ru.sicampus.bootcamp2026.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.dto.MeetingInputDTO;
import ru.sicampus.bootcamp2026.dto.UserDTO;

import java.util.List;

public interface MeetingService {
    List<MeetingDTO> getAllMeeting();

    MeetingDTO getMeetingById(Long id);

    MeetingDTO createMeeting(Long userId, MeetingInputDTO dto);

    MeetingDTO updateMeeting(Long id, Long userId, MeetingInputDTO dto);

    void deleteMeeting(Long id);

    List<UserDTO> getAllMemberOfMeeting(Long id);

    Page<MeetingDTO> getAllMeetingPaginated(Pageable pageable);

    Pageable buildPage(int page, int size);
}
