package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Поиск пользователя по email
     */
    Optional<User> findByEmail(String email);

    /**
     * Проверка существования пользователя по email
     */
    boolean existsByEmail(String email);

    /**
     * Поиск пользователя по имени пользователя (username)
     */
    Optional<User> findByUsername(String username);

    /**
     * Получение всех пользователей, кроме текущего (для выбора участников) — с пагинацией.
     */
    @Query("SELECT u FROM User u WHERE u.id != :currentUserId")
    Page<User> findAllExceptCurrentUser(@Param("currentUserId") UUID currentUserId, Pageable pageable);
}
