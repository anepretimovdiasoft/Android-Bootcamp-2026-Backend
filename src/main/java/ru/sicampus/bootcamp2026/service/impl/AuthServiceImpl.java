package ru.sicampus.bootcamp2026.service.impl;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.config.JwtConfig;
import ru.sicampus.bootcamp2026.dto.mapper.UserMapper;
import ru.sicampus.bootcamp2026.dto.request.LoginRequest;
import ru.sicampus.bootcamp2026.dto.request.RegisterRequest;
import ru.sicampus.bootcamp2026.dto.response.AuthResponse;
import ru.sicampus.bootcamp2026.exception.SecurityException;
import ru.sicampus.bootcamp2026.exception.UserNotFoundException;
import ru.sicampus.bootcamp2026.model.CustomUserDetails;
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
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtServiceImpl;
    private final JwtConfig jwtConfig;
    private final AuthenticationManager authenticationManager;


    @Override
    public AuthResponse register(RegisterRequest request) {
        User user = userMapper.fromRegisterRequest(request);
        user.setHashedPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
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
        log.info("User registered with id: {}", user.getId());
        return userMapper.toAuthResponse(user, accessToken, refreshToken.getToken(), accessExpiration, refreshExpiration);

    }

    @Override
    @Transactional
    public AuthResponse authenticate(LoginRequest request) {
        try {
            // Если данные неверные — authenticationManager бросит AuthenticationException.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.user();

            Instant now = Instant.now();
            Instant refreshExpiration = now.plusMillis(jwtConfig.getRefreshTokenExpirationMs());
            Instant accessExpiration = now.plusMillis(jwtConfig.getAccessTokenExpirationMs());

            // Берём максимальную версию среди валидных токенов пользователя и увеличиваем на 1.
            long nextTokenVersion = refreshTokenRepository.findAllValidTokenByUserId(user.getId())
                    .stream()
                    .mapToLong(RefreshToken::getTokenVersion)
                    .max()
                    .orElse(0L) + 1L;

            String refreshTokenValue = jwtServiceImpl.generateRefreshToken(user, now, refreshExpiration);
            RefreshToken refreshToken = RefreshToken.builder()
                    .user(user)
                    .tokenVersion(nextTokenVersion)
                    .token(refreshTokenValue)
                    .expiresAt(refreshExpiration)
                    .build();
            refreshTokenRepository.save(refreshToken);

            String accessToken = jwtServiceImpl.generateAccessToken(user, refreshToken, now, accessExpiration);

            return userMapper.toAuthResponse(
                    user,
                    accessToken,
                    refreshToken.getToken(),
                    accessExpiration,
                    refreshExpiration
            );
        } catch (AuthenticationException e) {
            log.warn("Failed to authenticate user with email {}: {}", request.getEmail(), e.getMessage());
            throw e;
        }
    }


    @Override
    @Transactional
    public void logout(UUID userId) {
        // Отзываем все активные refresh-токены пользователя.
        // После этого refresh/access токены этой "сессии" больше нельзя будет обновить.
        refreshTokenRepository.revokeAllByUserId(userId);
        log.info("User {} logged out: all refresh tokens revoked", userId);
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(String refreshTokenValue) {
        if (refreshTokenValue == null || refreshTokenValue.isBlank()) {
            throw new SecurityException("Refresh token is empty");
        }

        //Проверка подписи
        if (!jwtServiceImpl.tokenIsValid(refreshTokenValue)) {
            throw new SecurityException("Invalid refresh token");
        }
        //Проверка рефреш токена
        RefreshToken stored = refreshTokenRepository.findByToken(refreshTokenValue)
                .orElseThrow(() -> new SecurityException("Refresh token not found"));

        if (stored.isRevoked()) {
            throw new SecurityException("Refresh token revoked");
        }
        if (stored.isExpired()) {
            refreshTokenRepository.revokeByToken(refreshTokenValue);
            throw new SecurityException("Refresh token expired");
        }

        //Извлекаем userId из subject refresh JWT и сверяем с БД.
        Claims claims = jwtServiceImpl.extractAllClaims(refreshTokenValue);
        UUID userId;
        try {
            userId = UUID.fromString(claims.getSubject());
        } catch (Exception e) {
            throw new SecurityException("Invalid refresh token subject (userId)");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found for refresh token"));

        //Дополнительная защита: убеждаемся, что токен в БД принадлежит этому пользователю.
        if (stored.getUser() != null && stored.getUser().getId() != null && !stored.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Refresh token does not belong to this user");
        }

        //Генерируем новый access token. Refresh токен не меняем
        Instant now = Instant.now();
        Instant accessExpiration = now.plusMillis(jwtConfig.getAccessTokenExpirationMs());

        String newAccessToken = jwtServiceImpl.generateAccessToken(user, stored, now, accessExpiration);

        return userMapper.toAuthResponse(
                user,
                newAccessToken,
                stored.getToken(),
                accessExpiration,
                stored.getExpiresAt()
        );
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