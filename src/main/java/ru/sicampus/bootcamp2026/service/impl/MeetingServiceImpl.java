package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.request.MeetingCreateDTO;
import ru.sicampus.bootcamp2026.dto.response.MeetingResponseDTO;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.MeetingException;
import ru.sicampus.bootcamp2026.mapper.MeetingMapper;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.util.SecurityUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository repository;

    @Override
    public MeetingResponseDTO createMeeting(MeetingCreateDTO dto) throws MeetingException {
        User user = SecurityUtils.getCurrentUser();

        var conflicts = repository.findMeetingsByOrganizerTimeConflict(user.getId(), dto.getTimeStart(), dto.getTimeEnd());

        if (!conflicts.isEmpty()) {
            throw MeetingException.timeConflict(conflicts.stream().map(MeetingMapper::convertToDto).toList());
        }

        Meeting meeting = new Meeting(
                user,
                dto.getTitle(),
                dto.getDescription(),
                dto.getTimeStart(),
                dto.getTimeEnd()
        );
        return MeetingMapper.convertToDto(repository.save(meeting));
    }

    @Override
    public List<MeetingResponseDTO> getSchedule() {
        User user = SecurityUtils.getCurrentUser();
        Instant now = Instant.now();

        return repository.findMeetingSchedule(user.getId(), now, now.plus(30, ChronoUnit.DAYS))
                .stream()
                .map(MeetingMapper::convertToDto)
                .toList();
    }

    @Override
    public void deleteMeeting(long id) throws MeetingException {
        User user = SecurityUtils.getCurrentUser();
        Meeting meeting = repository.findById(id).orElseThrow(MeetingException::notFound);

        if (meeting.getOrganizer().getId() != user.getId()) {
            throw MeetingException.accessDenied();
        }

        repository.delete(meeting);
    }
}
