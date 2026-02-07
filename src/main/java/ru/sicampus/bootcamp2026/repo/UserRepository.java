package ru.sicampus.bootcamp2026.repo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.domain.User;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByLogin(String login);

    User getUserById(Long id);

    boolean findByLogin(@NotBlank @Size(max = 255) String login);

    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
}