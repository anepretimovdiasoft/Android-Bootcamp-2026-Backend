package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.model.RefreshToken;
import ru.sicampus.bootcamp2026.model.User;

public interface RefreshTokenService {

    // create
    RefreshToken createRefreshToken(User user);

    // logout
    void logout(String refreshTokenValue);
}
