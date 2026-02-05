package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.UserDTO;
import ru.sicampus.bootcamp2026.dto.UserRegisterDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO getUserByEmail(String email);
    UserDTO createUser(UserRegisterDTO dto);
    UserDTO updateUser(Long id, UserDTO dto);
    UserDTO getUserByUsername(String username);
    void deleteUser(Long id);
    boolean existsByEmail(String email);
}