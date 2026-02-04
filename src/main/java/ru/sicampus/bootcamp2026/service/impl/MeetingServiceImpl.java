package ru.sicampus.bootcamp2026.service.impl;

import ru.sicampus.bootcamp2026.dto.request.UserCreateDTO;
import ru.sicampus.bootcamp2026.dto.request.UserUpdateDTO;
import ru.sicampus.bootcamp2026.dto.response.UserResponseDTO;
import ru.sicampus.bootcamp2026.exception.UserExistsException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.service.MeetingService;

import java.util.List;

public class MeetingServiceImpl implements MeetingService {

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return List.of();
    }

    @Override
    public UserResponseDTO getUserById(long id) throws UserNotFoundException {
        return null;
    }

    @Override
    public UserResponseDTO createUser(UserCreateDTO dto) throws UserExistsException {
        return null;
    }

    @Override
    public UserResponseDTO updateUser(long id, UserUpdateDTO dto) throws UserNotFoundException {
        return null;
    }

    @Override
    public void deleteUser(long id) {

    }
}
