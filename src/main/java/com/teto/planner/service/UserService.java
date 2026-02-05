package com.teto.planner.service;

import com.teto.planner.dto.LoadStatus;
import com.teto.planner.dto.PageMeta;
import com.teto.planner.dto.UserDto;
import com.teto.planner.dto.UserMeDto;
import com.teto.planner.dto.UserSummaryDto;
import com.teto.planner.dto.UsersPage;
import com.teto.planner.entity.UserEntity;
import com.teto.planner.exception.BadRequestException;
import com.teto.planner.exception.NotFoundException;
import com.teto.planner.mapper.UserMapper;
import com.teto.planner.repository.BusyHoursProjection;
import com.teto.planner.repository.MeetingParticipantRepository;
import com.teto.planner.repository.UserRepository;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final MeetingParticipantRepository meetingParticipantRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            MeetingParticipantRepository meetingParticipantRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.meetingParticipantRepository = meetingParticipantRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UsersPage listUsers(String query, LocalDate busyDate, boolean includeLoad, int page, int size) {
        Page<UserEntity> usersPage;
        PageRequest pageable = PageRequest.of(page, size);
        if (query != null && !query.isBlank()) {
            usersPage = userRepository.findByLoginContainingIgnoreCaseOrNameContainingIgnoreCase(query, query, pageable);
        } else {
            usersPage = userRepository.findAll(pageable);
        }

        Map<UUID, Integer> busyHours = new HashMap<>();
        if (includeLoad && busyDate != null && !usersPage.getContent().isEmpty()) {
            List<UUID> ids = usersPage.getContent().stream().map(UserEntity::getId).toList();
            List<BusyHoursProjection> rows = meetingParticipantRepository.sumBusyHours(ids, busyDate);
            for (BusyHoursProjection row : rows) {
                busyHours.put(row.getUserId(), row.getHours().intValue());
            }
        }

        List<UserSummaryDto> items = usersPage.getContent().stream()
                .map(u -> {
                    Integer hours = includeLoad ? busyHours.getOrDefault(u.getId(), 0) : null;
                    LoadStatus status = includeLoad ? toLoadStatus(hours) : null;
                    return userMapper.toSummary(u, hours, status);
                })
                .collect(Collectors.toList());

        PageMeta meta = new PageMeta(page, size, usersPage.getTotalElements());
        return new UsersPage(items, meta);
    }

    public UserDto getUser(UUID userId) {
        return userMapper.toDto(findUser(userId));
    }

    @Transactional
    public UserDto createUser(String login, String name, String password, String telegramNick) {
        if (login == null || login.isBlank()) {
            throw new BadRequestException("VALIDATION_ERROR", "login is required");
        }
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setLogin(login);
        user.setName(name);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setTelegramNick(telegramNick);
        UserEntity saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Transactional
    public UserDto updateUser(UUID userId, String name, String telegramNick) {
        UserEntity user = findUser(userId);
        if (name != null) {
            user.setName(name);
        }
        if (telegramNick != null) {
            user.setTelegramNick(telegramNick);
        }
        return userMapper.toDto(user);
    }

    @Transactional
    public void deleteUser(UUID userId) {
        UserEntity user = findUser(userId);
        userRepository.delete(user);
    }

    @Transactional
    public UserMeDto updateMe(UserEntity user, String name, String telegramNick) {
        if (name != null) {
            user.setName(name);
        }
        if (telegramNick != null) {
            user.setTelegramNick(telegramNick);
        }
        return userMapper.toMe(user);
    }

    public UserMeDto toMe(UserEntity user) {
        return userMapper.toMe(user);
    }

    @Transactional
    public UserMeDto updateAvatar(UserEntity user, byte[] bytes, String contentType) {
        user.setAvatarBytes(bytes);
        user.setAvatarContentType(contentType);
        return userMapper.toMe(user);
    }

    public byte[] getAvatar(UUID userId) {
        UserEntity user = findUser(userId);
        if (user.getAvatarBytes() == null || user.getAvatarBytes().length == 0) {
            throw new NotFoundException("AVATAR_NOT_FOUND", "User has no avatar");
        }
        return user.getAvatarBytes();
    }

    public UserEntity findUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND", "User not found"));
    }

    private LoadStatus toLoadStatus(Integer hours) {
        if (hours == null) {
            return null;
        }
        if (hours <= 2) {
            return LoadStatus.LOW;
        }
        if (hours <= 5) {
            return LoadStatus.MEDIUM;
        }
        return LoadStatus.HIGH;
    }
}
