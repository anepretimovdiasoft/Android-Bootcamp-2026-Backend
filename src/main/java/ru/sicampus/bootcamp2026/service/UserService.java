package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.UserDto;

public interface UserService {

    UserDto getUserById(Long id);

    UserDto createUser(UserDto dto);

    UserDto updateUser(Long id, UserDto dto);

    void deleteUser(Long id);
}
