package ru.sicampus.bootcamp2026.service.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.MeetingsDTO;
import ru.sicampus.bootcamp2026.dto.UsersDTO;
import ru.sicampus.bootcamp2026.entity.Meetings;
import ru.sicampus.bootcamp2026.entity.Users;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.MeetingsRepository;
import ru.sicampus.bootcamp2026.repository.UsersRepository;
import ru.sicampus.bootcamp2026.service.MeetingsService;
import ru.sicampus.bootcamp2026.util.MeetingsMapper;
import ru.sicampus.bootcamp2026.util.UsersMapper;

@RequiredArgsConstructor
@Service
public class MeetingsServiceImpl implements MeetingsService {
    private final MeetingsRepository meetingsRepository;
    private final UsersRepository usersRepository;

    @Override
    public MeetingsDTO getMeetingById(long id) {
        return meetingsRepository.findById(id).map(MeetingsMapper::convertToDTO)
                .orElseThrow(()->new MeetingNotFoundException("Meeting not found"));
    }

    @Override
    public MeetingsDTO createMeeting(MeetingsDTO dto) {
        Users creator = usersRepository.findByName(dto.getCreatorName()).orElseThrow(()-> new UserNotFoundException("User not found"));
        Meetings meeting = new Meetings();
        meeting.setCreatorId(creator);
        meeting.setDate(dto.getDate());
        return MeetingsMapper.convertToDTO(meetingsRepository.save(meeting));
    }

    @Override
    public MeetingsDTO updateUser(long id, MeetingsDTO dto) {
        Meetings meeting = meetingsRepository.findById(id).orElseThrow(()->new MeetingNotFoundException("Meeting not found"));
        Users creator = usersRepository.findByName(dto.getCreatorName()).orElseThrow(()-> new UserNotFoundException("User not found"));
        meeting.setCreatorId(creator);
        meeting.setDate(dto.getDate());
        return MeetingsMapper.convertToDTO(meetingsRepository.save(meeting));
    }

    @Override
    public void deleteUser(long id) {
        Meetings meeting = meetingsRepository.findById(id).orElseThrow(()->new MeetingNotFoundException("Meeting not found"));
        meetingsRepository.delete(meeting);
    }

    @Override
    public Page<MeetingsDTO> getAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return meetingsRepository.findAll(pageable).map(MeetingsMapper::convertToDTO);
    }
}