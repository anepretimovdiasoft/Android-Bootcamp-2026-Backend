package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.MeetingsDTO;
import ru.sicampus.bootcamp2026.dto.UsersDTO;

import java.util.List;

public interface UsersService {
    List<UsersDTO> getAllUsers();

    UsersDTO getUsersById(Long id);

    UsersDTO createUsers(UsersDTO dto);

    UsersDTO updateUsers(Long id, UsersDTO dto);

    void deleteUsers(Long id);
}
