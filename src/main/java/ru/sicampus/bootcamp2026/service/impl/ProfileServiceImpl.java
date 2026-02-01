package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.request.UserProfileRequest;
import ru.sicampus.bootcamp2026.dto.response.UserProfileResponse;
import ru.sicampus.bootcamp2026.service.ProfileService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    /*
        TODO: Внедрить репозиторий позже
    */

    @Override
    public UserProfileResponse getProfile(UUID userId) {
        /*
            TODO: Реализовать получение профиля пользователя
        */
        throw new UnsupportedOperationException("Метод getProfile еще не реализован");
    }

    @Override
    public UserProfileResponse updateProfile(UUID userId, UserProfileRequest request) {
        /*
            TODO: Реализовать обновление профиля пользователя
        */
        throw new UnsupportedOperationException("Метод updateProfile еще не реализован");
    }

    @Override
    public UserProfileResponse getPublicProfile(UUID userId) {
        /*
            TODO: Реализовать получение публичного профиля
        */
        throw new UnsupportedOperationException("Метод getPublicProfile еще не реализован");
    }

    @Override
    public List<UserProfileResponse> getAllUsers(UUID currentUserId) {
        /*
            TODO: Реализовать получение списка всех пользователей
        */
        throw new UnsupportedOperationException("Метод getAllUsers еще не реализован");
    }
}
