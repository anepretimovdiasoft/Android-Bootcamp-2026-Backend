package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.MeetingsDTO;
import ru.sicampus.bootcamp2026.entity.Meetings;
import ru.sicampus.bootcamp2026.entity.Users;
import ru.sicampus.bootcamp2026.exeption.MeetingsNotFoundException;
import ru.sicampus.bootcamp2026.repository.MeetingsRepository;
import ru.sicampus.bootcamp2026.repository.UsersRepository;
import ru.sicampus.bootcamp2026.service.MeetingsService;
import ru.sicampus.bootcamp2026.util.MeetingsMapper;

import java.nio.file.ProviderNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingsServiceImpl implements MeetingsService {

    private final MeetingsRepository meetingsRepository;
    private final UsersRepository usersRepository;

    @Override
    public List<MeetingsDTO> getAllMeetings() {
        return meetingsRepository.findAll().stream()
                .map(MeetingsMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MeetingsDTO getMeetingsById(Long id) {
        return meetingsRepository.findById(id).map(MeetingsMapper::convertToDto)
                .orElseThrow(() -> new MeetingsNotFoundException("Meeting not found!!!"));
    }

    @Override
    public MeetingsDTO createMeetings(MeetingsDTO dto) {
        //Optional<Users> optionalUsers = usersRepository.findById(dto.) вроде это не надо 1:45

        Meetings meetings = new Meetings();
        meetings.setTopic(dto.getTopic());
        meetings.setDescription(dto.getDescription());
        meetings.setCreator(dto.getCreator());
        meetings.setDate(dto.getDate());
        meetings.setTimeStart(dto.getTimeStart());
        meetings.setDuration(dto.getDuration());
        meetings.setPlace(dto.getPlace());
        meetings.setStatus(dto.getStatus());

        return MeetingsMapper.convertToDto(meetingsRepository.save(meetings));
    }

    @Override
    public MeetingsDTO updateMeetings(Long id, MeetingsDTO dto) {
       Meetings meetings = meetingsRepository.findById(id).orElseThrow(() -> new MeetingsNotFoundException("Meeting not found!!"));

        meetings.setTopic(dto.getTopic());
        meetings.setDescription(dto.getDescription());
        meetings.setCreator(dto.getCreator());
        meetings.setDate(dto.getDate());
        meetings.setTimeStart(dto.getTimeStart());
        meetings.setDuration(dto.getDuration());
        meetings.setPlace(dto.getPlace());
        meetings.setStatus(dto.getStatus());

        return MeetingsMapper.convertToDto(meetingsRepository.save(meetings));
    }

    @Override
    public void deleteMeetings(Long id) {
        meetingsRepository.deleteById(id);
    }
}
