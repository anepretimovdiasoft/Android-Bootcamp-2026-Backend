package ru.sicampus.bootcamp2026.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sicampus.bootcamp2026.dto.request.UpdateAvatarRequest;
import ru.sicampus.bootcamp2026.dto.request.UserProfileRequest;
import ru.sicampus.bootcamp2026.dto.response.UserProfileResponse;

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
     * Обновление аватара пользователя
     */
    UserProfileResponse updateAvatar(UUID userId, UpdateAvatarRequest request);

    /**
     * Получение публичного профиля пользователя
     */
    UserProfileResponse getPublicProfile(UUID userId);

    /**
     * Получение списка пользователей (кроме текущего) с пагинацией.
     */
    Page<UserProfileResponse> getAllUsers(UUID currentUserId, Pageable pageable);
}
