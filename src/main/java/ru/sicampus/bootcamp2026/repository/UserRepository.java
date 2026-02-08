package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.sicampus.bootcamp2026.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.secondName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.position) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.department) LIKE LOWER(CONCAT('%', :search, '%'))" +
            "AND u.id != :userId")
    Page<User> search(@Param("search") String search, Long userId, Pageable pageable);

}
