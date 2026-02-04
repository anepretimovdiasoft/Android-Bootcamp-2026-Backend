package ru.sicampus.bootcamp2026.repository;
import ru.sicampus.bootcamp2026.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByAuthority(String authority);
}