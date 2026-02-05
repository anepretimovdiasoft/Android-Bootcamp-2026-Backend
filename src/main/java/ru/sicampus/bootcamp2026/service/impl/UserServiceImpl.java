package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.UserDTO;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.UserService;
import ru.sicampus.bootcamp2026.util.UserMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::convertToDto)
                .orElseThrow(() -> new UserNotFoundException("User not found!!!"));
    }

    @Override
    public UserDTO createUser(UserDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPasswordHash()));
        user.setFullName(dto.getFullName());
        user.setPfpUrl(dto.getPfpUrl());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return UserMapper.convertToDto(savedUser);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!!!"));

        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPasswordHash()));
        user.setFullName(dto.getFullName());
        user.setPfpUrl(dto.getPfpUrl());
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        return UserMapper.convertToDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
