package ru.sicampus.bootcamp2026.service;


import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.UserDto;
import ru.sicampus.bootcamp2026.dto.UserUpdateDto;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    UserDto getUserById(Long id);

    List<UserDto> getAllUsers();

    Optional<UserDto> updateUser(Long id, UserUpdateDto updateDto);
}
