package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.UserDto;
import ru.sicampus.bootcamp2026.dto.UserRegisterDto;

public interface UserService {

    UserDto getUserById(Long id);

    UserDto createUser(UserRegisterDto dto);

    UserDto updateUser(String login, UserDto dto);

    void deleteUser(String login);

    UserDto getUserByLogin(String login);
}
