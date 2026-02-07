package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDTO> getAllDepartments();
}
