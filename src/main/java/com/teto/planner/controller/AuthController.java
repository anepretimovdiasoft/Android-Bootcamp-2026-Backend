package com.teto.planner.controller;

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
}
