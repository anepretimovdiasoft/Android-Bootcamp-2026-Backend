package ru.sicampus.bootcamp2026.service;

import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.dto.request.UserProfileRequest;
import ru.sicampus.bootcamp2026.dto.response.UserProfileResponse;

import java.util.List;
import java.util.UUID;

public interface ProfileService {

    /**
     * Получение профиля пользователя
     */
    UserProfileResponse getProfile(UUID userId);

    /**
     * Обновление профиля пользователя
     */
    UserProfileResponse updateProfile(UUID userId, UserProfileRequest request);

    /**
     * Получение публичного профиля пользователя
     */
    UserProfileResponse getPublicProfile(UUID userId);

    /**
     * Получение списка всех пользователей
     */
    List<UserProfileResponse> getAllUsers(UUID currentUserId);
}
