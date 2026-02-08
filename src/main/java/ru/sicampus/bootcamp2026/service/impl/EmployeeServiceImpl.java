package ru.sicampus.bootcamp2026.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.EmployeeDTO;
import ru.sicampus.bootcamp2026.dto.EmployeeEditDTO;
import ru.sicampus.bootcamp2026.dto.EmployeeRegisterDTO;
import ru.sicampus.bootcamp2026.entity.Employee;
import ru.sicampus.bootcamp2026.exception.EmployeeAlreadyExistsException;
import ru.sicampus.bootcamp2026.exception.EmployeeNotFoundException;
import ru.sicampus.bootcamp2026.repository.AuthorityRepository;
import ru.sicampus.bootcamp2026.repository.EmployeeRepository;
import ru.sicampus.bootcamp2026.service.EmployeeService;
import ru.sicampus.bootcamp2026.util.EmployeeMapper;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        employee.setPassword(passwordEncoder.encode(employeeRegisterDTO.getPassword()));
        employee.setAuthorities(Set.of(authorityRepository.findById(1L).get()));

        return EmployeeMapper.convertToDTO(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDTO editEmployee(EmployeeEditDTO employeeEditDTO, String username) {
        Employee employee = employeeRepository.findByUsername(username);
        if(!Objects.equals(employeeEditDTO.getEmail(), employee.getEmail()) && employeeRepository.existsByEmail(employeeEditDTO.getEmail())) {
            throw new EmployeeAlreadyExistsException("Employee with the same email is already registered");
        }
        if(!Objects.equals(employeeEditDTO.getPhoneNumber(), employee.getPhoneNumber()) && employeeRepository.existsByPhoneNumber(employeeEditDTO.getPhoneNumber())) {
            throw new EmployeeAlreadyExistsException("Employee with the same phone number is already registered");
        }
        String name = employeeEditDTO.getName();
        String position = employeeEditDTO.getPosition();
        String email = employeeEditDTO.getEmail();
        String phoneNumber = employeeEditDTO.getPhoneNumber();
        String photoUrl = employeeEditDTO.getPhotoUrl();
        if(name != null) {
            employee.setName(name);
        }
        if(position != null) {
            employee.setPosition(position);
        }
        if(email != null) {
            employee.setEmail(email);
        }
        if(phoneNumber != null) {
            employee.setPhoneNumber(phoneNumber);
        }
        if(photoUrl != null) {
            employee.setPhotoUrl(photoUrl);
        }

        return EmployeeMapper.convertToDTO(employeeRepository.save(employee));
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
        if(search == null) {
            return employeeRepository.findAll().stream().map(EmployeeMapper::convertToDTO).toList();
        }
        return employeeRepository.findByNameContainsIgnoreCase(search).stream().map(EmployeeMapper::convertToDTO).toList();
    }

    @Override
    public Page<EmployeeDTO> searchEmployeesPaginated(String search, Pageable pageable) {
        if(search == null) {
            return employeeRepository.findAll(pageable).map(EmployeeMapper::convertToDTO);
        }
        return employeeRepository.findByNameContainsIgnoreCase(search, pageable).map(EmployeeMapper::convertToDTO);
    }

    @Override
    public void deleteEmployee(String username) {
        Employee employee = employeeRepository.findByUsername(username);
        if(employee == null) {
            throw new EmployeeNotFoundException("Employee Not Found");
        }
        employeeRepository.delete(employee);
    }

}
