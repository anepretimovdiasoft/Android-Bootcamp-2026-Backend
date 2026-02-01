package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.model.RefreshToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    /**
     * Поиск токена
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Получение всех активных токенов пользователя
     */
    @Query("SELECT rt FROM RefreshToken rt " +
            "WHERE rt.userId.id = :userId AND rt.revoked = false AND rt.expiresAt > CURRENT_TIMESTAMP")
    List<RefreshToken> findActiveByUserId(@Param("userId") UUID userId);

    /**
     * Забрать все токены пользователя
     */
    @Query("UPDATE RefreshToken rt SET rt.revoked = true WHERE rt.userId.id = :userId")
    void revokeAllByUserId(@Param("userId") UUID userId);

    /**
     * Отзыв токена
     */
    @Query("UPDATE RefreshToken rt SET rt.revoked = true WHERE rt.token = :token")
    void revokeByToken(@Param("token") String token);
}
