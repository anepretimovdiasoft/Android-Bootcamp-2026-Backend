package ru.sicampus.bootcamp2026.service.impl;

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


    @Transactional
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
        log.info("User registered with id {}", user.getId());
        return userMapper.toAuthResponse(user, accessToken, refreshToken.getToken(), accessExpiration, refreshExpiration);

    }
    @Transactional
    @Override
    public AuthResponse authenticate(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.user();

            Instant now = Instant.now();
            Instant refreshExpiration = now.plusMillis(jwtConfig.getRefreshTokenExpirationMs());
            Instant accessExpiration = now.plusMillis(jwtConfig.getAccessTokenExpirationMs());

            // Версионирование refresh-токенов: при каждом логине выдаём новую версию.
            // Берём максимальную версию среди ещё валидных токенов пользователя и увеличиваем на 1.
            long nextTokenVersion = refreshTokenRepository.findAllValidTokenByUserId(user.getId())
                    .stream()
                    .mapToLong(RefreshToken::getTokenVersion)
                    .max()
                    .orElse(0L) + 1L;//если не быдл токенов

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
        } catch (AuthenticationException ex) {
            // 401.
            throw ex;
        }

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