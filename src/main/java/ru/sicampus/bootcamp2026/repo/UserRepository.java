package ru.sicampus.bootcamp2026.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(Long id);
}