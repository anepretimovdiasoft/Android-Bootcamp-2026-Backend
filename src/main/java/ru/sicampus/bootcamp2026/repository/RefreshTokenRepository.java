package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.model.RefreshToken;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    //Найти токен
    Optional<RefreshToken> findByToken(String token);

    //Отозвать все токены пользователя
    @Modifying//Пометка для Spring Data JPA, что это запрос на изменение данных, а не выборка SELECT
    @Query(value = """
    UPDATE refresh_tokens
        SET revoked = true
            WHERE user_id = :userId""", nativeQuery = true)
    void revokeAllByUserId(@Param("userId") UUID userId);

    //Отозвать конкретный токен
    @Modifying
    @Query(value = """
    UPDATE refresh_tokens
        SET revoked = true
            WHERE token = :token""", nativeQuery = true)
    void revokeByToken(@Param("token") String token);

    //Получение всех валидных токенов пользователя
    @Query(value = """
    SELECT * FROM refresh_tokens
                 WHERE user_id = :userId
                       AND revoked = false
                       AND expires_at > now()""", nativeQuery = true)
    List<RefreshToken> findAllValidTokenByUserId(@Param("userId") UUID userId);
}
