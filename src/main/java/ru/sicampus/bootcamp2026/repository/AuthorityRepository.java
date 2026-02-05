package ru.sicampus.bootcamp2026.repository;

import java.util.Optional;
import ru.sicampus.bootcamp2026.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByName(String name);
}

