package ru.sicampus.bootcamp2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.request.AuthRequest;
import ru.sicampus.bootcamp2026.dto.request.RegisterRequest;
import ru.sicampus.bootcamp2026.dto.response.AuthResponse;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    /*
        TODO: Внедрить потом сервис(ы)
    */

    /**
     * Регистрация нового пользователя
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        /*
            TODO: Реализовать регистрацию пользователя
        */
        throw new UnsupportedOperationException("Метод register еще не реализован");
    }

    /**
     * Авторизация пользователя
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        /*
            TODO: Реализовать аутентификацию пользователя
        */
        throw new UnsupportedOperationException("Метод login еще не реализован");
    }

    /**
     * Выход из системы (отзыв токенов)
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        /*
            TODO: Получить текущего пользователя и забрать токены
        */
        throw new UnsupportedOperationException("Метод logout еще не реализован");
    }

    /**
     * Обновление access токена с помощью refresh токена
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestHeader("Authorization") String authorizationHeader) {
        /*
            TODO: Извлечь refresh токен и сгенерировать новый access токен
        */
        throw new UnsupportedOperationException("Метод refresh еще не реализован");
    }
}
