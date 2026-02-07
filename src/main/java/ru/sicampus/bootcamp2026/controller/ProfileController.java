package ru.sicampus.bootcamp2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.request.ResetPasswordRequest;
import ru.sicampus.bootcamp2026.dto.request.UpdateAvatarRequest;
import ru.sicampus.bootcamp2026.dto.request.UserProfileRequest;
import ru.sicampus.bootcamp2026.dto.response.UserProfileResponse;
import ru.sicampus.bootcamp2026.model.CustomUserDetails;
import ru.sicampus.bootcamp2026.service.ProfileService;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    /**
     * Получение профиля текущего пользователя
     */
    @GetMapping
    public ResponseEntity<UserProfileResponse> getProfile(
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        UserProfileResponse profile = profileService.getProfile(currentUser.user().getId());
        return ResponseEntity.ok(profile);
    }

    /**
     * Обновление профиля текущего пользователя
     */
    @PutMapping
    public ResponseEntity<UserProfileResponse> updateProfile(
            @Valid @RequestBody UserProfileRequest request,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        UserProfileResponse updatedProfile = profileService.updateProfile(
                currentUser.user().getId(),
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
            @AuthenticationPrincipal CustomUserDetails currentUser
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
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        UserProfileResponse updatedProfile = profileService.updateAvatar(
                currentUser.user().getId(),
                request
        );
        return ResponseEntity.ok(updatedProfile);
    }

    /**
     * Получение списка всех пользователей для выбора участников встречи (поиск)
     */
    @GetMapping("/public/all")
    public ResponseEntity<Page<UserProfileResponse>> getAllUsers(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            Pageable pageable
    ) {
        Page<UserProfileResponse> users = profileService.getAllUsers(currentUser.user().getId(), pageable);
        return ResponseEntity.ok(users);
    }

}
