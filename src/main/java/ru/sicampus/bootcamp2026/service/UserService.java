package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.fromApp.NewUserDTO;
import ru.sicampus.bootcamp2026.dto.toApp.UserDTO;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO createUser(NewUserDTO dto);
    UserDTO updateUser(UserDTO dto);
    void deleteUser(Long id);
    UserDTO getUserByEmail(String email);
}
