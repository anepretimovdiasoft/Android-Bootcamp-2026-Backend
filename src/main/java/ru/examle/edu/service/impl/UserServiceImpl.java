package ru.examle.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.examle.edu.dto.UserDTO;
import ru.examle.edu.entity.User;
import ru.examle.edu.repository.UserRepository;
import ru.examle.edu.service.UserService;
import ru.examle.edu.ulti.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("User with email " + userDTO.getEmail() + " already exists");
        }
        User user = userMapper.toEntity(userDTO);
        if (userDTO.getPasswordHash() != null && !userDTO.getPasswordHash().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(userDTO.getPasswordHash()));
        }
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (!existingUser.getEmail().equals(userDTO.getEmail()) && userRepository.existsByEmail(userDTO.getEmail())) {
             throw new RuntimeException("Email " + userDTO.getEmail() + " is already taken");
        }

        // Update fields
        existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getPasswordHash() != null && !userDTO.getPasswordHash().isEmpty()) {
            existingUser.setPasswordHash(passwordEncoder.encode(userDTO.getPasswordHash()));
        }
        existingUser.setFullName(userDTO.getFullName());
        existingUser.setPosition(userDTO.getPosition());
        existingUser.setDepartment(userDTO.getDepartment());
        existingUser.setAvatarUrl(userDTO.getAvatarUrl());
        if(userDTO.getRole() != null) existingUser.setRole(userDTO.getRole());
        existingUser.setNotificationSettings(userDTO.getNotificationSettings());
        if(userDTO.getWorkHoursStart() != null) existingUser.setWorkHoursStart(userDTO.getWorkHoursStart());
        if(userDTO.getWorkHoursEnd() != null) existingUser.setWorkHoursEnd(userDTO.getWorkHoursEnd());
        existingUser.setActive(userDTO.isActive());

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDTO(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
