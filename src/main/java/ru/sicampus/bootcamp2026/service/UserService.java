package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.request.UserCreateDTO;
import ru.sicampus.bootcamp2026.dto.response.UserResponseDTO;
import ru.sicampus.bootcamp2026.dto.request.UserUpdateDTO;
import ru.sicampus.bootcamp2026.exception.UserExistsException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(long id) throws UserNotFoundException;
    UserResponseDTO getUserByEmail(String email) throws UserNotFoundException;
    UserResponseDTO createUser(UserCreateDTO dto) throws UserExistsException;
    UserResponseDTO updateUser(long id, UserUpdateDTO dto) throws UserNotFoundException;
    void deleteUser(long id);
}
