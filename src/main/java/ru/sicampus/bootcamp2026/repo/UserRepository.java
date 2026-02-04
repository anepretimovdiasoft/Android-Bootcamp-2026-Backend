package ru.sicampus.bootcamp2026.repo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByLogin(String login);
    User getUserById(Long id);

    boolean findByLogin(@NotBlank @Size(max = 255) String login);
}