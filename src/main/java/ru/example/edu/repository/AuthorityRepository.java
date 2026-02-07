package ru.example.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.edu.entity.Authority;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByAuthority(String authority);
}
