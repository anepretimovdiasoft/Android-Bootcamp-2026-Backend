package ru.sicampus.bootcamp2026.service;

import io.jsonwebtoken.Claims;
import ru.sicampus.bootcamp2026.model.RefreshToken;
import ru.sicampus.bootcamp2026.model.User;

import java.time.Instant;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {

   public <T> T extractClaim(String token, Function<Map<String, Object>, T> claimsResolver);

   public Claims extractAllClaims(String token);

   public String generateAccessToken(User user, RefreshToken refreshToken);

   public String generateRefreshToken(User user, Instant now, Instant expiration);

   public boolean tokenIsValid(String token);
}
