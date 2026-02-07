package ru.sicampus.bootcamp2026.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.domain.ConfirmationToken;
import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);
}