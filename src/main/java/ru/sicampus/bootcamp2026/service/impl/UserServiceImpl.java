package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.domain.*;
import ru.sicampus.bootcamp2026.dto.MeetingDtos.MeetingResponse;
import ru.sicampus.bootcamp2026.dto.UserDtos.*;
import ru.sicampus.bootcamp2026.error.*;
import ru.sicampus.bootcamp2026.repo.*;
import ru.sicampus.bootcamp2026.service.UserService;
import ru.sicampus.bootcamp2026.util.MeetingMapper;
import ru.sicampus.bootcamp2026.util.UserMapper;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMeetingRepository usMetRepo;
    private static final Set<String> ALLOWED_STATUSES = Set.of("ACCEPTED", "REJECTED", "PENDING");
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UserResponse> getAllUserPaginated(String search, Pageable pageable) {
        Page<User> userPage;
        if (search == null || search.isBlank()) {
            userPage = userRepository.findAll(pageable);
        } else {
            userPage = userRepository.findByNameContainingIgnoreCase(search, pageable);
        }
        return userPage.map(UserMapper::toResponse);
    }

    @Override
    public List<UserResponse> list() {
        return userRepository.findAll().stream().map(UserMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public InvitationDecisionRequest decideInvitation(long userId, long meetingId, InvitationDecisionRequest req) {
        UserMeetingId id = new UserMeetingId(userId, meetingId);
        UserMeeting um = usMetRepo.findById(id).orElseThrow(() -> new InvitationNotFoundException("Invitation not found"));
        um.setStatus(req.status());
        usMetRepo.save(um);
        return new InvitationDecisionRequest(um.getStatus());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeetingResponse> listMeetingsByStatus(long userId, String status) {
        if (!userRepository.existsById(userId)) throw new UserNotFoundException("User not found: " + userId);
        String normalized = status == null ? "" : status.trim().toUpperCase();
        if (!ALLOWED_STATUSES.contains(normalized)) throw new WrongInvitationException("Invalid status");
        return usMetRepo.findMeetingsByUserIdAndStatus(userId, normalized).stream().map(MeetingMapper::toResponse).toList();
    }

    @Override
    public UserResponse get(long id) {
        User u = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found: " + id));
        return UserMapper.toResponse(u);
    }

    @Override
    public UserResponse getByLogin(String login) {
        User u = userRepository.getUserByLogin(login).orElseThrow(() -> new UserNotFoundException("user not found!"));
        return UserMapper.toResponse(u);
    }

    @Override
    @Transactional
    public UserResponse create(CreateUserRequest req) {
        if (userRepository.getUserByLogin(req.login()).isPresent()) throw new UserAlreadyExistsException("login already exists");
        Authority roleUser = authorityRepository.getAuthorityByAuthority("ROLE_USER").orElseThrow(() -> new RuntimeException("Authority not found"));
        User u = User.builder()
                .position(req.position())
                .name(req.name())
                .login(req.login())
                .authorities(Set.of(roleUser))
                .password(passwordEncoder.encode((req.password())))
                .build();
        u = userRepository.save(u);
        return UserMapper.toResponse(u);
    }

    @Override
    @Transactional
    public UserResponse update(long id, UpdateUserRequest req) {
        User u = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found: " + id));
        u.setPosition(req.position());
        u.setLogin(req.login());
        u.setName(req.name());
        u.setPhone(req.phone());
        u.setBirthDate(req.birthDate());
        userRepository.save(u);
        return UserMapper.toResponse(u);
    }

    @Override
    @Transactional
    public void delete(long id) {
        if (!userRepository.existsById(id)) throw new UserNotFoundException("User not found: " + id);
        usMetRepo.deleteByUser(userRepository.getUserById(id));
        userRepository.deleteById(id);
    }
}