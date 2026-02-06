package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.config.JwtConfig;
import ru.sicampus.bootcamp2026.dto.request.LoginRequest;
import ru.sicampus.bootcamp2026.dto.request.RegisterRequest;
import ru.sicampus.bootcamp2026.dto.response.AuthResponse;
import ru.sicampus.bootcamp2026.model.RefreshToken;
import ru.sicampus.bootcamp2026.model.Role;
import ru.sicampus.bootcamp2026.model.User;
import ru.sicampus.bootcamp2026.repository.RefreshTokenRepository;
import ru.sicampus.bootcamp2026.repository.UserRepository;
import ru.sicampus.bootcamp2026.service.AuthService;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtServiceImpl;
    private final JwtConfig jwtConfig;


    @Override
    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .hashedPassword(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        int tokenVersion = 0;
        Instant now = Instant.now();
        Instant refreshExpiration = now.plusMillis(jwtConfig.getRefreshTokenExpirationMs());
        Instant accessExpiration = now.plusMillis(jwtConfig.getAccessTokenExpirationMs());

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .tokenVersion(tokenVersion)
                .token(jwtServiceImpl.generateRefreshToken(user, now, refreshExpiration))
                .expiresAt(refreshExpiration)
                .build();

        refreshTokenRepository.save(refreshToken);

        String accessToken = jwtServiceImpl.generateAccessToken(user, refreshToken, now, accessExpiration);
        log.info("User registerd with id {}" + user.getId());

        return AuthResponse.fromUserAndTokens(
                user,
                accessToken,
                refreshToken.getToken(),
                accessExpiration,
                refreshExpiration
        );
    }

    @Override
    public AuthResponse authenticate(LoginRequest request) {
        /*
            TODO: Реализовать аутентификацию пользователя
        */
        throw new UnsupportedOperationException("Метод authenticate еще не реализован");
    }

    @Override
    public void logout(UUID userId) {
        /*
            TODO: Реализовать выход из системы
        */
        throw new UnsupportedOperationException("Метод logout еще не реализован");
    }

    @Override
    public AuthResponse refreshToken(String refreshTokenValue) {
        /*
            TODO: Реализовать обновление токена
        */
        throw new UnsupportedOperationException("Метод refreshToken еще не реализован");
    }

    @Override
    public UUID getCurrentUserId() {
        /*
            TODO: Реализовать получение текущего пользователя из контекста безопасности
        */
        throw new UnsupportedOperationException("Метод getCurrentUserId еще не реализован");
    }

    @Override
    public void resetPassword(UUID userId) {

    }
}