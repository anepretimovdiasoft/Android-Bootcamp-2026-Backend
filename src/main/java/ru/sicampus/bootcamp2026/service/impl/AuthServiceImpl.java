package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.request.AuthRequest;
import ru.sicampus.bootcamp2026.dto.request.RegisterRequest;
import ru.sicampus.bootcamp2026.dto.response.AuthResponse;
import ru.sicampus.bootcamp2026.service.AuthService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    /*
        TODO: Внедрить репозиторий позже
    */

    @Override
    public AuthResponse register(RegisterRequest request) {
        /*
            TODO: Реализовать регистрацию пользователя
        */
        throw new UnsupportedOperationException("Метод register еще не реализован");
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        /*
            TODO: Реализовать аутентификацию пользователя
        */
        throw new UnsupportedOperationException("Метод authenticate еще не реализован");
    }

    @Override
    public void logout(UUID userId) {
        /*
            TODO: Реализовать выход из системы
        */
        throw new UnsupportedOperationException("Метод logout еще не реализован");
    }

    @Override
    public AuthResponse refreshToken(String refreshTokenValue) {
        /*
            TODO: Реализовать обновление токена
        */
        throw new UnsupportedOperationException("Метод refreshToken еще не реализован");
    }

    @Override
    public UUID getCurrentUserId() {
        /*
            TODO: Реализовать получение текущего пользователя из контекста безопасности
        */
        throw new UnsupportedOperationException("Метод getCurrentUserId еще не реализован");
    }
}