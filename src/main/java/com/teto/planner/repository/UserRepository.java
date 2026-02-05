package com.teto.planner.repository;

import com.teto.planner.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByLoginIgnoreCase(String login);

    Page<UserEntity> findByLoginContainingIgnoreCaseOrNameContainingIgnoreCase(
            String login,
            String name,
            Pageable pageable
    );
}
