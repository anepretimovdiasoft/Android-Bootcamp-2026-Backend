package ru.sicampus.bootcamp2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.request.ResetPasswordRequest;
import ru.sicampus.bootcamp2026.dto.request.UpdateAvatarRequest;
import ru.sicampus.bootcamp2026.dto.request.UserProfileRequest;
import ru.sicampus.bootcamp2026.dto.response.UserProfileResponse;
import ru.sicampus.bootcamp2026.model.User;
import ru.sicampus.bootcamp2026.service.JwtService;
import ru.sicampus.bootcamp2026.service.ProfileService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final JwtService jwtService;

    /**
     * Получение профиля текущего пользователя
     */
    @GetMapping
    public ResponseEntity<UserProfileResponse> getProfile(
            @AuthenticationPrincipal User currentUser
    ) {
        UserProfileResponse profile = profileService.getProfile(currentUser.getId());
        return ResponseEntity.ok(profile);
    }

    /**
     * Обновление профиля текущего пользователя
     */
    @PutMapping
    public ResponseEntity<UserProfileResponse> updateProfile(
            @Valid @RequestBody UserProfileRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        UserProfileResponse updatedProfile = profileService.updateProfile(
                currentUser.getId(),
                request
        );
        return ResponseEntity.ok(updatedProfile);
    }

    /**
     * Сброс пароля
     */
    @PutMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        /*
            TODO: Реализовать сброс пароля
        */
        throw new UnsupportedOperationException("Метод сброса пароля не реализован");
    }

    /**
     * Обновление аватара
     */
    @PutMapping("/avatar")
    public ResponseEntity<UserProfileResponse> updateAvatar(
            @Valid @RequestBody UpdateAvatarRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        UserProfileResponse updatedProfile = profileService.updateAvatar(
                currentUser.getId(),
                request
        );
        return ResponseEntity.ok(updatedProfile);
    }

    /**
     * Получение списка всех пользователей для выбора участников встречи (поиск)
     */
    @GetMapping("/public/all")
    public ResponseEntity<List<UserProfileResponse>> getAllUsers(
            @AuthenticationPrincipal User currentUser
    ) {
        List<UserProfileResponse> users = profileService.getAllUsers(currentUser.getId());
        return ResponseEntity.ok(users);
    }

}
