package ru.sicampus.bootcamp2026.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.InvitationEmployeeDTO;
import ru.sicampus.bootcamp2026.dto.InvitationMeetingDTO;
import ru.sicampus.bootcamp2026.dto.MeetingCreateDTO;
import ru.sicampus.bootcamp2026.dto.MeetingDTO;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundExeception;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.util.InvitationEmployeeMapper;
import ru.sicampus.bootcamp2026.util.MeetingMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeetingServiceImpl implements MeetingService {
    @Autowired
    MeetingRepository meetingRepository;

    @Override
    public MeetingDTO createMeeting(MeetingCreateDTO meetingCreateDTO) {
        return null;
    }

    @Override
    public MeetingDTO getMeetingByID(Long Id) {
        Optional<Meeting> meeting = meetingRepository.findById(Id);
        if (meeting.isEmpty()) {
            throw new MeetingNotFoundExeception("Meeting not found");
        }
        return MeetingMapper.convertToDTO(meeting.get());
    }

    @Override
    public List<MeetingDTO> getSchedule(LocalDateTime start, LocalDateTime end) {
        return List.of();
    }

    @Override
    public List<InvitationEmployeeDTO> getEmployeesByMeetingID(Long Id) {
        Optional<Meeting> meeting = meetingRepository.findById(Id);
        if (meeting.isEmpty()) {
            throw new MeetingNotFoundExeception("Meeting not found");
        }

        return meeting.get().getInvitations().stream().map(InvitationEmployeeMapper::convertToDTO).toList();
    }
}
