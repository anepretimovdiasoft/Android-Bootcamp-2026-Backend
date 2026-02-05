package ru.sicampus.bootcamp2026.service.impl;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.config.JwtConfig;
import ru.sicampus.bootcamp2026.model.RefreshToken;
import ru.sicampus.bootcamp2026.model.User;
import ru.sicampus.bootcamp2026.service.JwtService;
import ru.sicampus.bootcamp2026.util.JwtUtil;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

    private final JwtUtil jwtUtil;
    private JwtParser jwtParser;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;

    void init(){
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(jwtUtil.getPublicKey())//установка ключа для проверки на соответствие
                .build();//создаем экземпляр парсера
    }

    @Override
    public <T> T extractClaim(String token, Function<Map<String, Object>, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
                return claimsResolver.apply(claims);
    }

    @Override
    public Claims extractAllClaims(String token) {
        return jwtParser
                .parseClaimsJws(token)//сверяет при помощи публичного ключа подпись токена и извлекает его payload (Claims)
                .getBody();
    }

    @Override
    public String generateAccessToken(User user, RefreshToken refreshToken, Instant now, Instant expiration) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim("role", user.getRole())
                .claim("token_version", refreshToken.getTokenVersion())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(jwtUtil.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    @Override
    public String generateRefreshToken(User user, Instant now, Instant expiration) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(jwtUtil.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    @Override
    public boolean tokenIsValid(String token) {
        try{
            jwtParser
                    .parseClaimsJws(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            log.warn("Invalid jwt token: {}", e.getMessage());
            return false;
            }
    }
}
