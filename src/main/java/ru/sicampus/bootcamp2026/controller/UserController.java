package ru.sicampus.bootcamp2026.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.UserDto;
import ru.sicampus.bootcamp2026.dto.UserUpdateDto;
import ru.sicampus.bootcamp2026.exception.AccessDeniedException;
import ru.sicampus.bootcamp2026.service.UserService;

import java.util.List;


@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> userProfile(@PathVariable("id") Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping() // для приглашения на встречи
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("id") Long id,
            @RequestBody UserUpdateDto updateDto,
            Authentication authentication
    ) {

        String currentEmail = authentication.getName();
        UserDto targetUser = userService.getUserById(id);

        if (!currentEmail.equals(targetUser.getEmail())) {
            throw new AccessDeniedException("You can only update your own profile");
        }
        return userService.updateUser(id, updateDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
