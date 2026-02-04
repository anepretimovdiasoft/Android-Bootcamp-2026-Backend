package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.request.UserCreateDTO;
import ru.sicampus.bootcamp2026.dto.response.UserResponseDTO;
import ru.sicampus.bootcamp2026.dto.request.UserUpdateDTO;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.exception.UserExistsException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.mapper.UserMapper;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(UserMapper::convertToDto)
                .toList();
    }

    @Override
    public UserResponseDTO getUserById(long id) throws UserNotFoundException {
        return repository.findById(id)
                .map(UserMapper::convertToDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public UserResponseDTO createUser(UserCreateDTO dto) throws UserExistsException {
        var user = repository.findByEmail(dto.getEmail());
        if (user.isPresent()) {
            throw new UserExistsException(dto.getEmail());
        }

        return UserMapper.convertToDto(repository.save(new User(
                dto.getEmail(),
                dto.getFullName(),
                dto.getPassword()
        )));
    }

    @Override
    public UserResponseDTO updateUser(long id, UserUpdateDTO dto) throws UserNotFoundException {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if (dto.getFullName() != null) user.setFullName(dto.getFullName());
        if (dto.getAvatarUrl() != null) user.setAvatarUrl(dto.getAvatarUrl());
        return UserMapper.convertToDto(repository.save(user));
    }

    @Override
    public void deleteUser(long id) {
        repository.deleteById(id);
    }
}
