package ru.sicampus.bootcamp2026.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sicampus.bootcamp2026.dto.UserRegisterDTO;
import ru.sicampus.bootcamp2026.dto.UsersDTO;

public interface UsersService {

    UsersDTO getUserById(long id);

    UsersDTO createUser(UserRegisterDTO dto);

    UsersDTO getUserByUsername(String username);

    UsersDTO updateUser(long id, UsersDTO dto);

    void deleteUser(long id);

    Page<UsersDTO> getAllUsersPaginated(Pageable pageable);
}