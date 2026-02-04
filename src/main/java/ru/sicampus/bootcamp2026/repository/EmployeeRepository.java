package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @EntityGraph(attributePaths = {"authorities"})
    Employee findByUsername(String username);
    boolean existsByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);
    Page<Employee> findByNameContainsIgnoreCase(String name, Pageable pageable);
    List<Employee> findByNameContainsIgnoreCase(String name);
}