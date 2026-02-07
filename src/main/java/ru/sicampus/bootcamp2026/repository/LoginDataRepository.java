package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entity.LoginData;

import java.util.Optional;

public interface LoginDataRepository extends JpaRepository<LoginData, Long> {
    @EntityGraph(attributePaths = {"user", "user.authorities"})
    Optional<LoginData> findByEmail(String email);
    boolean existsByEmail(String email);
}
