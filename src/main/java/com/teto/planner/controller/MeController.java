package com.teto.planner.controller;

import com.teto.planner.dto.AvatarUploadResponse;
import com.teto.planner.dto.UpdateUserRequest;
import com.teto.planner.dto.UserMeDto;
import com.teto.planner.entity.UserEntity;
import com.teto.planner.service.CurrentUserService;
import com.teto.planner.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me")
public class MeController {
    private final CurrentUserService currentUserService;
    private final UserService userService;

    public MeController(CurrentUserService currentUserService, UserService userService) {
        this.currentUserService = currentUserService;
        this.userService = userService;
    }

    @GetMapping
    public UserMeDto getMe() {
        UserEntity user = currentUserService.getCurrentUser();
        return userService.toMe(user);
    }

    @PatchMapping
    public UserMeDto patchMe(@Valid @RequestBody UpdateUserRequest request) {
        UserEntity user = currentUserService.getCurrentUser();
        return userService.updateMe(user, request.name(), request.telegramNick(), request.bio());
    }

    @PutMapping(value = "/avatar", consumes = {"image/jpeg", "image/png"})
    public ResponseEntity<AvatarUploadResponse> putAvatar(
            @RequestBody byte[] bytes,
            @RequestHeader("Content-Type") String contentType
    ) {
        UserEntity user = currentUserService.getCurrentUser();
        UserMeDto updated = userService.updateAvatar(user, bytes, contentType);
        return ResponseEntity.ok(new AvatarUploadResponse(
                updated.avatarUrl(),
                updated.avatarContentType()
        ));
    }
}
