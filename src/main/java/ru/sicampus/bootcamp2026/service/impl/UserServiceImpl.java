package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.ProfileUpdateDTO;
import ru.sicampus.bootcamp2026.dto.UsersDTO;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.UserService;
import ru.sicampus.bootcamp2026.util.UserMapper;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void updateProfile(Long userId, ProfileUpdateDTO dto) {
        if (dto.getFullName() != null && !dto.getFullName().matches("^[a-zA-Zа-яА-Я\\s\\-]+$")) {
            throw new RuntimeException("Некорректное имя");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (dto.getFullName() != null) user.setFullName(dto.getFullName());
        if (dto.getContactInfo() != null) user.setContactInfo(dto.getContactInfo());
        if (dto.getAvatarUrl() != null) user.setAvatarUrl(dto.getAvatarUrl());

        userRepository.save(user);
    }
    @Override
    public UsersDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return userMapper.toDTO(user);
    }
}