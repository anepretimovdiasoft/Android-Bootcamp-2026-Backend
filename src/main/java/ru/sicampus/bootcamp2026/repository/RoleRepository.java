package ru.sicampus.bootcamp2026.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entity.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);
}