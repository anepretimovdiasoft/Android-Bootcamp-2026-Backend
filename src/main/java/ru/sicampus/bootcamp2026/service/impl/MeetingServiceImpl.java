package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.domain.Meeting;
import ru.sicampus.bootcamp2026.domain.User;
import ru.sicampus.bootcamp2026.domain.UserMeeting;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.CreateMeetingRequest;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.MeetingResponse;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.UpdateMeetingRequest;
import ru.sicampus.bootcamp2026.error.NotFoundException;
import ru.sicampus.bootcamp2026.repo.MeetingRepository;
import ru.sicampus.bootcamp2026.repo.UserMeetingRepository;
import ru.sicampus.bootcamp2026.repo.UserRepository;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.util.MeetingMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository repo;
    private final UserMeetingRepository usMetRepo;
    private final UserRepository userRepo;

    @Override
    public List<MeetingResponse> list() {
        return repo.findAll().stream()
                .map(MeetingMapper::toResponse)
                .toList();
    }

    @Override
    public MeetingResponse get(long id) {
        Meeting m = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Meeting not found: " + id));
        return MeetingMapper.toResponse(m);
    }

    @Override
    @Transactional
    public MeetingResponse create(CreateMeetingRequest req) {
        Meeting meeting = Meeting.builder()
                .title(req.title())
                .startsAt(req.startsAt())
                .endsAt(req.endsAt())
                .build();

        meeting = repo.save(meeting);

        List<User> users = userRepo.findAllById(req.invitedUserIds());
        if (users.size() != req.invitedUserIds().size()) {
            throw new IllegalArgumentException("Some users not found");
        }

        for (User u : users) {
            UserMeeting link = new UserMeeting();
            link.setUser(u);
            link.setMeeting(meeting);
            link.setAccepted("PENDING");
            usMetRepo.save(link);
        }

        return MeetingMapper.toResponse(meeting);
    }

    @Override
    @Transactional
    public MeetingResponse update(long id, UpdateMeetingRequest req) {
        Meeting m = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Meeting not found: " + id));

        m.setTitle(req.title());
        m.setStartsAt(req.startsAt());
        m.setEndsAt(req.endsAt());

        return MeetingMapper.toResponse(m);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Meeting meeting = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Meeting not found: " + id));

        usMetRepo.deleteByMeeting(meeting);
        repo.delete(meeting);
    }
}