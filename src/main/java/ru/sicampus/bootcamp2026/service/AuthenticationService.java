package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.AuthResponse;
import ru.sicampus.bootcamp2026.dto.RegisterRequest;

public interface AuthenticationService {
    public AuthResponse register(RegisterRequest registerRequest);
}
