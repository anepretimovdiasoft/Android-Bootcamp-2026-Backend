package ru.sicampus.bootcamp2026.service;

import ru.sicampus.bootcamp2026.dto.EmployeeDTO;
import ru.sicampus.bootcamp2026.dto.EmployeeEditDTO;
import ru.sicampus.bootcamp2026.dto.EmployeeRegisterDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeRegisterDTO employeeRegisterDTO);
    EmployeeDTO editEmployee(EmployeeEditDTO employeeEditDTO, String username);
    EmployeeDTO getEmployeeByUsername(String username);
    List<EmployeeDTO> searchEmployees(String search);
}
