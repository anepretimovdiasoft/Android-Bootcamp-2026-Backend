package ru.examle.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.examle.edu.dto.MeetingDTO;
import ru.examle.edu.entity.Meeting;
import ru.examle.edu.repository.MeetingRepository;
import ru.examle.edu.service.MeetingService;
import ru.examle.edu.ulti.MeetingMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final MeetingMapper meetingMapper;

    @Override
    public List<MeetingDTO> getAllMeetings() {
        return meetingRepository.findAll().stream()
                .map(meetingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MeetingDTO getMeetingById(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meeting not found with id: " + id));
        return meetingMapper.toDTO(meeting);
    }

    @Override
    @Transactional
    public MeetingDTO createMeeting(MeetingDTO meetingDTO) {
        Meeting meeting = meetingMapper.toEntity(meetingDTO);
        Meeting savedMeeting = meetingRepository.save(meeting);
        return meetingMapper.toDTO(savedMeeting);
    }

    @Override
    @Transactional
    public MeetingDTO updateMeeting(Long id, MeetingDTO meetingDTO) {
        if (!meetingRepository.existsById(id)) {
            throw new RuntimeException("Meeting not found with id: " + id);
        }
        meetingDTO.setId(id); // Ensure ID matches
        Meeting meeting = meetingMapper.toEntity(meetingDTO);
        Meeting updatedMeeting = meetingRepository.save(meeting);
        return meetingMapper.toDTO(updatedMeeting);
    }

    @Override
    @Transactional
    public void deleteMeeting(Long id) {
        if (!meetingRepository.existsById(id)) {
            throw new RuntimeException("Meeting not found with id: " + id);
        }
        meetingRepository.deleteById(id);
    }
}
