package ru.sicampus.bootcamp2026.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private UUID userId;
    private String username;
    private String email;
    private String accessToken;
    private String refreshToken;
    private Instant accessTokenExpiresAt;
    private Instant refreshTokenExpiresAt;
}
