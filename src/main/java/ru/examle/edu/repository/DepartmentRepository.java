package ru.examle.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.examle.edu.enity.Department;

import java.util.Optional;

@Repository
public interface    DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String name);

}
