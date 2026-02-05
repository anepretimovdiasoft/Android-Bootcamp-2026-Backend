package ru.sicampus.bootcamp2026.service.impl;

import ru.sicampus.bootcamp2026.dto.userDTO;
import ru.sicampus.bootcamp2026.entity.AppUserEntity;
import ru.sicampus.bootcamp2026.exception.ResourceNotFoundException;
import ru.sicampus.bootcamp2026.repository.userRepository;
import ru.sicampus.bootcamp2026.service.userService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class userServiceImpl implements userService {
    private final userRepository userRepository;

    @Override
    public userDTO createUser(userDTO userDTO) {
        AppUserEntity user = mapToEntity(userDTO);
        AppUserEntity savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public userDTO getUserById(Long id) {
        AppUserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapToDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<userDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public userDTO updateUser(Long id, userDTO userDTO) {
        AppUserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPhotoUrl(userDTO.getPhotoUrl());
        user.setPosition(userDTO.getPosition());

        AppUserEntity updatedAppUsers = userRepository.save(user);
        return mapToDTO(updatedAppUsers);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private AppUserEntity mapToEntity(userDTO dto) {
        AppUserEntity user = new AppUserEntity();
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPhotoUrl(dto.getPhotoUrl());
        user.setPosition(dto.getPosition());
        return user;
    }

    private userDTO mapToDTO(AppUserEntity user) {
        userDTO dto = new userDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setPhotoUrl(user.getPhotoUrl());
        dto.setPosition(user.getPosition());
        return dto;
    }
}