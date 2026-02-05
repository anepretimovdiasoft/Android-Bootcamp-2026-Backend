package ru.sicampus.bootcamp2026.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.UserDto;
import ru.sicampus.bootcamp2026.dto.UserUpdateDto;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.model.entity.Users;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.UserService;
import ru.sicampus.bootcamp2026.util.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public UserDto getUserById(Long id) {
        return repository.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public Optional<UserDto> updateUser(Long id, UserUpdateDto updateDto) {
        return repository.findById(id)
                .map(user -> {
                    if (updateDto.getEmail() != null) user.setEmail(updateDto.getEmail());
                    if (updateDto.getUsername() != null) user.setUsername(updateDto.getUsername());
                    if (updateDto.getPosition() != null) user.setPosition(updateDto.getPosition());
                    if (updateDto.getPassword() != null) {
                        user.setPassword(updateDto.getPassword());
                    }

                    Users savedUser = repository.save(user);
                    return UserMapper.toDto(savedUser);
                });
    }
}
