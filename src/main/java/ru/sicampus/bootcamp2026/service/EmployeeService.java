package ru.sicampus.bootcamp2026.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sicampus.bootcamp2026.dto.EmployeeDTO;
import ru.sicampus.bootcamp2026.dto.EmployeeEditDTO;
import ru.sicampus.bootcamp2026.dto.EmployeeRegisterDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeRegisterDTO employeeRegisterDTO);
    EmployeeDTO editEmployee(EmployeeEditDTO employeeEditDTO, String username);
    EmployeeDTO getEmployeeByUsername(String username);
    List<EmployeeDTO> searchEmployees(String search);
    Page<EmployeeDTO> searchEmployeesPaginated(String search, Pageable pageable);
}
