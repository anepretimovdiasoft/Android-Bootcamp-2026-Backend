package ru.sicampus.bootcamp2026.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.Entity.Employee;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByName(String name);
    List<Employee> findAll();
    Optional<Employee> findByMail(String mail);
    boolean existsByMail(String mail);
}
