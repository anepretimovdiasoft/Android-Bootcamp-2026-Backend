package ru.sicampus.bootcamp2026.service;

import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.dto.request.AuthRequest;
import ru.sicampus.bootcamp2026.dto.request.RegisterRequest;
import ru.sicampus.bootcamp2026.dto.response.AuthResponse;

import java.util.UUID;

public interface AuthService {

    /**
     * Регистрация нового пользователя
     */
    AuthResponse register(RegisterRequest request);

    /**
     * Аутентификация пользователя
     */
    AuthResponse authenticate(AuthRequest request);

    /**
     * Выход из системы
     */
    void logout(UUID userId);

    /**
     * Обновление токена доступа
     */
    AuthResponse refreshToken(String refreshTokenValue);

    /**
     * Получение текущего пользователя из контекста безопасности
     */
    UUID getCurrentUserId();

    void resetPassword(UUID userId);
}
