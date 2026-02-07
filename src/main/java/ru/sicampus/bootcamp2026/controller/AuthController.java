package ru.sicampus.bootcamp2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.AuthDtos.*;
import ru.sicampus.bootcamp2026.dto.UserDtos.CreateUserRequest;
import ru.sicampus.bootcamp2026.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody @Valid CreateUserRequest req) {
        return authService.register(req);
    }

    @PostMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        return authService.confirmToken(token);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestBody ForgotPasswordRequest req) {
        authService.forgotPassword(req);
    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody ResetPasswordRequest req) {
        authService.resetPassword(req);
    }
}