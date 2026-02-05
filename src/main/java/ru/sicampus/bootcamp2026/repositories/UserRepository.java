package ru.sicampus.bootcamp2026.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
