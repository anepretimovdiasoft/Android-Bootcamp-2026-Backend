package ru.sicampus.bootcamp2026.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sicampus.bootcamp2026.dto.UserRegisterDTO;
import ru.sicampus.bootcamp2026.dto.UsersDTO;

import java.util.List;

public interface UserService {
    List<UsersDTO> getAllUsers();
    Page<UsersDTO> getAllUsers(Pageable pageable);
    UsersDTO getUserById(Long id);

    UsersDTO createUser(UserRegisterDTO dto); // Регистрация

    UsersDTO updateProfile(Long id, UsersDTO dto); // Обновление

    void deleteUser(Long id);

    UsersDTO getUserByEmail(String email);
}