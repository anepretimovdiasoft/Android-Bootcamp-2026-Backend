package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.userDTO;
import java.util.List;

public interface userService {
    userDTO createUser(userDTO userDTO);
    userDTO getUserById(Long id);
    List<userDTO> getAllUsers();
    userDTO updateUser(Long id, userDTO userDTO);
    void deleteUser(Long id);
}