package ru.sicampus.bootcamp2026.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.EmployeeDTO;
import ru.sicampus.bootcamp2026.dto.EmployeeEditDTO;
import ru.sicampus.bootcamp2026.dto.EmployeeRegisterDTO;
import ru.sicampus.bootcamp2026.entity.Employee;
import ru.sicampus.bootcamp2026.exception.EmployeeAlreadyExistsException;
import ru.sicampus.bootcamp2026.exception.EmployeeNotFoundException;
import ru.sicampus.bootcamp2026.repository.EmployeeRepository;
import ru.sicampus.bootcamp2026.service.EmployeeService;
import ru.sicampus.bootcamp2026.util.EmployeeMapper;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO createEmployee(EmployeeRegisterDTO employeeRegisterDTO) {
        if(employeeRepository.existsByUsernameOrEmailOrPhoneNumber(employeeRegisterDTO.getUsername(),
                employeeRegisterDTO.getEmail(), employeeRegisterDTO.getPhoneNumber())) {
            throw new EmployeeAlreadyExistsException("Employee with the same credentials is already registered");
        }
        Employee employee = new Employee();
        employee.setName(employeeRegisterDTO.getName());
        employee.setPosition(employeeRegisterDTO.getPosition());
        employee.setUsername(employeeRegisterDTO.getUsername());
        employee.setEmail(employeeRegisterDTO.getEmail());
        employee.setPhoneNumber(employeeRegisterDTO.getPhoneNumber());
        employee.setPassword(employeeRegisterDTO.getPassword());

        return EmployeeMapper.convertToDTO(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDTO editEmployee(EmployeeEditDTO employeeEditDTO) {
        return null;
    }

    @Override
    public EmployeeDTO getEmployeeByUsername(String username) {
        Employee employee = employeeRepository.findByUsername(username);
        if(employee == null) {
            throw new EmployeeNotFoundException("Employee Not Found");
        }
        return EmployeeMapper.convertToDTO(employee);
    }

    @Override
    public List<EmployeeDTO> searchEmployees(String search) {
        return employeeRepository.findByNameStartsWithIgnoreCase(search).stream().map(EmployeeMapper::convertToDTO).toList();
    }
}
