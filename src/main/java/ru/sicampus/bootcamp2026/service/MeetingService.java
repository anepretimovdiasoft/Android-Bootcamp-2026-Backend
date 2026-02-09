package ru.sicampus.bootcamp2026.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.dto.MeetingInputDTO;
import ru.sicampus.bootcamp2026.dto.MemberDTO;
import ru.sicampus.bootcamp2026.entity.Users;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingService {
    List<MeetingDTO> getAllMeeting();

    MeetingDTO getMeetingById(Long id);

    MeetingDTO createMeeting(Users user, MeetingInputDTO dto);

    MeetingDTO updateMeeting(Long id, Users user, MeetingInputDTO dto);

    void deleteMeeting(Long id);

    List<MemberDTO> getAllMemberOfMeeting(Long id);

    Page<MeetingDTO> getAllMeetingPaginated(Pageable pageable);

    Pageable buildPage(int page, int size);

    List<MeetingDTO> getAllMeetingAfterNow(Users user);
}
