package ru.sicampus.bootcamp2026.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.request.LoginRequest;
import ru.sicampus.bootcamp2026.dto.request.RegisterRequest;
import ru.sicampus.bootcamp2026.dto.response.AuthResponse;
import ru.sicampus.bootcamp2026.exception.SecurityException;
import ru.sicampus.bootcamp2026.service.AuthService;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout(authService.getCurrentUserId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestHeader("Authorization") String authorizationHeader) {
        String refreshToken = extractBearerToken(authorizationHeader);
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    //Вытаскивает token из заголовка Authorization формата "Bearer <token>".
    private String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new SecurityException("Missing Authorization header");
        }
        String prefix = "Bearer ";
        if (!authorizationHeader.startsWith(prefix)) {
            throw new SecurityException("Authorization header must start with 'Bearer '");
        }
        String token = authorizationHeader.substring(prefix.length()).trim();
        if (token.isBlank()) {
            throw new SecurityException("Bearer token is empty");
        }
        return token;
    }
}
