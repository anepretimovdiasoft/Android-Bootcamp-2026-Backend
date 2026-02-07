package ru.sicampus.bootcamp2026.util.checkers;

import ru.sicampus.bootcamp2026.entity.Department;
import ru.sicampus.bootcamp2026.exception.DepartmentNotFoundException;
import ru.sicampus.bootcamp2026.repository.DepartmentRepository;

import java.util.Optional;

public class DepartmentChecker {
    public static Department checkDepartment(DepartmentRepository departmentRepository, String departmentName) {
        Optional<Department> optionalDepartment = departmentRepository.findByName(departmentName);
        if (optionalDepartment.isEmpty()) {
            throw new DepartmentNotFoundException();
        }
        return optionalDepartment.get();
    }
}
