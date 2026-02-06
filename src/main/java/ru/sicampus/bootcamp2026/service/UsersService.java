package ru.sicampus.bootcamp2026.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sicampus.bootcamp2026.dto.MeetingsDTO;
import ru.sicampus.bootcamp2026.dto.UsersDTO;
import ru.sicampus.bootcamp2026.dto.UsersRegisterDTO;

import java.util.List;

public interface UsersService {
    List<UsersDTO> getAllUsers();

    UsersDTO getUsersById(Long id);

    UsersDTO createUsers(UsersRegisterDTO dto);

    UsersDTO updateUsers(Long id, UsersDTO dto);

    void deleteUsers(Long id);

    UsersDTO getUsersByLogin(String login);

    Page<UsersDTO> getAllUsersPaginated(Pageable pageable);
}
