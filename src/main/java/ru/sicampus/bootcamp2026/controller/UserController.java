package ru.sicampus.bootcamp2026.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.*;
import ru.sicampus.bootcamp2026.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @PutMapping("/profile")
    public ResponseEntity<ApiResponseDTO> updateProfile(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId,
            @RequestBody ProfileUpdateDTO req) {
        userService.updateProfile(userId, req);
        return ResponseEntity.ok(new ApiResponseDTO("Профиль обновлен"));
    }
}