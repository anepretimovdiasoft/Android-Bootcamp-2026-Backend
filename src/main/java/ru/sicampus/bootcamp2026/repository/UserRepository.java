package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findAllBySurname(String surname);
    List<User> findAllByName(String name);
    List<User> findAllByPatronymic(String patronymic);
    List<User> findAllByDepartmentName(String departmentName);
}
