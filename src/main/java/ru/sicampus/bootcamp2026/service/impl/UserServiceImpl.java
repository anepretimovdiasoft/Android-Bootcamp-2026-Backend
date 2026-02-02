package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.domain.User;
import ru.sicampus.bootcamp2026.domain.UserMeeting;
import ru.sicampus.bootcamp2026.domain.UserMeetingId;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.MeetingResponse;
import ru.sicampus.bootcamp2026.dto.UserDtos.CreateUserRequest;
import ru.sicampus.bootcamp2026.dto.UserDtos.InvitationDecisionRequest;
import ru.sicampus.bootcamp2026.dto.UserDtos.UpdateUserRequest;
import ru.sicampus.bootcamp2026.dto.UserDtos.UserResponse;
import ru.sicampus.bootcamp2026.error.NotFoundException;
import ru.sicampus.bootcamp2026.repo.UserMeetingRepository;
import ru.sicampus.bootcamp2026.repo.UserRepository;
import ru.sicampus.bootcamp2026.service.UserService;
import ru.sicampus.bootcamp2026.util.MeetingMapper;
import ru.sicampus.bootcamp2026.util.UserMapper;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final UserMeetingRepository usMetRepo;
    private static final Set<String> ALLOWED_STATUSES = Set.of("ACCEPTED", "REJECTED", "PENDING");


    @Override
    public List<UserResponse> list() {
        return repo.findAll().stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public InvitationDecisionRequest decideInvitation(long userId, long meetingId, InvitationDecisionRequest req) {
        if (!repo.existsById(userId)) {
            throw new NotFoundException("User not found: " + userId);
        }

        UserMeetingId id = new UserMeetingId(userId, meetingId);

        UserMeeting um = usMetRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Invitation not found for user=" + userId + " meeting=" + meetingId
                ));

        um.setAccepted(req.status());
        usMetRepo.save(um);

        return new InvitationDecisionRequest(um.getAccepted());
    }


    @Override
    @Transactional(readOnly = true)
    public List<MeetingResponse> listMeetingsByStatus(long userId, String status) {
        if (!repo.existsById(userId)) {
            throw new NotFoundException("User not found: " + userId);
        }

        String normalized = status == null ? "" : status.trim().toUpperCase();
        if (!ALLOWED_STATUSES.contains(normalized)) {
            throw new IllegalArgumentException("Invalid status: " + status + ". Allowed: ACCEPTED|REJECTED|PENDING");
        }

        return usMetRepo.findMeetingsByUserIdAndStatus(userId, normalized).stream()
                .map(MeetingMapper::toResponse)
                .toList();
    }


    @Override
    public UserResponse get(long id) {
        User u = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));

        return new UserResponse(u.getId(), u.getRole(), u.getName(), u.getLogin());
    }

    @Override
    @Transactional
    public UserResponse create(CreateUserRequest req) {
        User u = User.builder()
                .role(req.role())
                .name(req.name())
                .login(req.login())
                .build();

        u = repo.save(u);
        return new UserResponse(u.getId(), u.getRole(), u.getName(), u.getLogin());
    }

    @Override
    @Transactional
    public UserResponse update(long id, UpdateUserRequest req) {
        User u = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));

        u.setRole(req.role());
        u.setLogin(req.login());
        u.setName(req.name());

        repo.save(u);

        return new UserResponse(u.getId(), u.getRole(), u.getName(), u.getLogin());
    }

    @Override
    @Transactional
    public void delete(long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("User not found: " + id);
        }

        usMetRepo.deleteByUser(repo.getUserById(id));
        repo.deleteById(id);
    }
}