package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.domain.Meeting;
import ru.sicampus.bootcamp2026.domain.User;
import ru.sicampus.bootcamp2026.domain.UserMeeting;
import ru.sicampus.bootcamp2026.domain.UserMeetingId;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.CreateMeetingRequest;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.MeetingParticipantResponse;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.MeetingResponse;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.UpdateMeetingRequest;
import ru.sicampus.bootcamp2026.error.MeetingConflictException;
import ru.sicampus.bootcamp2026.error.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.error.UserNotFoundException;
import ru.sicampus.bootcamp2026.repo.MeetingRepository;
import ru.sicampus.bootcamp2026.repo.UserMeetingRepository;
import ru.sicampus.bootcamp2026.repo.UserRepository;
import ru.sicampus.bootcamp2026.service.MeetingService;
import ru.sicampus.bootcamp2026.util.MeetingMapper;
import ru.sicampus.bootcamp2026.util.PastelColorGenerator;
import ru.sicampus.bootcamp2026.util.UserMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository repo;
    private final UserMeetingRepository usMetRepo;
    private final UserRepository userRepo;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public List<MeetingResponse> list() {
        return repo.findAll().stream()
                .map(MeetingMapper::toResponse)
                .toList();
    }

    @Override
    public MeetingResponse get(long id) {
        Meeting m = repo.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException("Meeting not found: " + id));
        return MeetingMapper.toResponse(m);
    }

    @Override
    public List<MeetingParticipantResponse> getParticipants(long meetingId) {
        if (!repo.existsById(meetingId)) {
            throw new MeetingNotFoundException("Meeting not found: " + meetingId);
        }
        return usMetRepo.findByMeeting_Id(meetingId).stream()
                .map(um -> {
                    var ur = UserMapper.toResponse(um.getUser());
                    return new MeetingParticipantResponse(
                            ur.id(),
                            ur.position(),
                            ur.name(),
                            ur.email(),
                            ur.phone(),
                            ur.birthDate(),
                            ur.avatarUrl(),
                            um.getStatus()
                    );
                })
                .toList();
    }

    @Override
    @Transactional
    public MeetingResponse create(CreateMeetingRequest req) {
        if (req.startsAt().getMinute() != 0 || req.endsAt().getMinute() != 0) {
            throw new IllegalArgumentException("Встречи должны начинаться и заканчиваться ровно в начало часа (XX:00)");
        }

        if (!req.startsAt().isBefore(req.endsAt())) {
            throw new IllegalArgumentException("Время начала должно быть раньше времени конца");
        }

        List<User> users = userRepo.findAllById(req.invitedUserIds());
        if (users.size() != req.invitedUserIds().size()) {
            throw new UserNotFoundException("Один или несколько пользователей не найдены");
        }

        for (User u : users) {
            boolean isBusy = usMetRepo.existsByUserIdAndTimeRange(
                    u.getId(), req.startsAt(), req.endsAt()
            );
            if (isBusy) {
                throw new MeetingConflictException("Пользователь " + u.getName() + " уже занят в это время!");
            }
        }

        String randomColor = PastelColorGenerator.randomHex();

        Meeting meeting = Meeting.builder()
                .title(req.title())
                .startsAt(req.startsAt())
                .endsAt(req.endsAt())
                .colorHex(randomColor)
                .description(req.description())
                .build();

        final Meeting savedMeeting = repo.save(meeting);

        for (User u : users) {
            UserMeeting link = new UserMeeting();

            link.setId(new UserMeetingId(u.getId(), savedMeeting.getId()));

            link.setUser(u);
            link.setMeeting(savedMeeting);
            link.setStatus("PENDING");
            usMetRepo.save(link);

            try {
                messagingTemplate.convertAndSend(
                        "/topic/invites/" + u.getId(),
                        "Вас пригласили на встречу: " + savedMeeting.getTitle()
                );
            } catch (Exception e) {
                System.err.println("Socket error: " + e.getMessage());
            }
        }

        return MeetingMapper.toResponse(savedMeeting);
    }

    @Override
    @Transactional
    public MeetingResponse update(long id, UpdateMeetingRequest req) {
        Meeting m = repo.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException("Meeting not found: " + id));

        m.setTitle(req.title());
        m.setStartsAt(req.startsAt());
        m.setEndsAt(req.endsAt());

        return MeetingMapper.toResponse(m);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Meeting meeting = repo.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException("Meeting not found: " + id));

        usMetRepo.deleteByMeeting(meeting);
        repo.delete(meeting);
    }
}