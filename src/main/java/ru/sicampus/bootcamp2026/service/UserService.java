package ru.sicampus.bootcamp2026.service;
import ru.sicampus.bootcamp2026.dto.ProfileUpdateDTO;
import ru.sicampus.bootcamp2026.dto.UsersDTO;

public interface UserService {
    void updateProfile(Long userId, ProfileUpdateDTO dto);
    UsersDTO getUserById(Long userId);
}