package com.teto.planner.controller;

import com.teto.planner.dto.CreateUserRequest;
import com.teto.planner.dto.LoginRequest;
import com.teto.planner.dto.UserMeDto;
import com.teto.planner.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @SecurityRequirements
    public UserMeDto login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request.login(), request.password());
    }

    @PostMapping("/register")
    @SecurityRequirements
    public UserMeDto register(@Valid @RequestBody CreateUserRequest request) {
        return authService.register(
                request.login(),
                request.name(),
                request.password(),
                request.telegramNick(),
                request.bio()
        );
    }
}
