package ru.sicampus.bootcamp2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.request.ResetPasswordRequest;
import ru.sicampus.bootcamp2026.dto.request.UpdateAvatarRequest;
import ru.sicampus.bootcamp2026.dto.request.UserProfileRequest;
import ru.sicampus.bootcamp2026.dto.response.UserProfileResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    /*
        TODO: Внедрить потом сервис(ы)
    */

    /**
     * Получение профиля текущего пользователя
     */
    @GetMapping
    public ResponseEntity<UserProfileResponse> getProfile() {
        /*
            TODO: Получить текущего пользователя из контекста безопасности
        */
        throw new UnsupportedOperationException("Метод getProfile еще не реализован");
    }

    /**
     * Обновление профиля текущего пользователя
     */
    @PutMapping
    public ResponseEntity<UserProfileResponse> updateProfile(@Valid @RequestBody UserProfileRequest request) {
        /*
            TODO: Получить текущего пользователя и обновить профиль
        */
        throw new UnsupportedOperationException("Метод updateProfile еще не реализован");
    }

    /**
     * Сброс пароля
     */
    @PutMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        /*
            TODO: Получить текущего пользователя и сбросить пароль
        */
        throw new UnsupportedOperationException("Метод сброса пароля не реализован");
    }

    /**
     * Обновление аватара
     */
    @PutMapping("/avatar")
    public ResponseEntity<UserProfileResponse> updateAvatar(@Valid @RequestBody UpdateAvatarRequest request) {
        /*
            TODO: Получить текущего пользователя и обновить аватар
        */
        throw new UnsupportedOperationException("Метод обновления аватара не реализован");
    }

    /**
     * Получение списка всех пользователей для выбора участников встречи
     */
    @GetMapping("/public/all")
    public ResponseEntity<List<UserProfileResponse>> getAllUsers() {
        /*
            TODO: Получить текущего пользователя и вернуть всех остальных
        */
        throw new UnsupportedOperationException("Метод getAllUsers еще не реализован");
    }

}
