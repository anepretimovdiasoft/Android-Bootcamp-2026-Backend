package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByUsername(String username);
    boolean existsByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);
    List<Employee> findByNameStartsWithIgnoreCase(String name);
}