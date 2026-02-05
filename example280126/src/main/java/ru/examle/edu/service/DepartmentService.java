package ru.examle.edu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.examle.edu.dto.*;
import ru.examle.edu.entity.Department;
import ru.examle.edu.entity.Person;
import ru.examle.edu.exception.ResourceNotFoundException;
import ru.examle.edu.exception.ValidationException;
import ru.examle.edu.repository.DepartmentRepository;
import ru.examle.edu.repository.PersonRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final PersonRepository personRepository;

    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public DepartmentDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        return convertToDto(department);
    }

    @Transactional
    public DepartmentDto createDepartment(DepartmentRequest request) {
        if (departmentRepository.existsByName(request.getName())) {
            throw new ValidationException("Department with name '" + request.getName() + "' already exists");
        }

        Department department = new Department();
        department.setName(request.getName());
        department.setCreatedAt(LocalDateTime.now());
        department.setUpdatedAt(LocalDateTime.now());

        department = departmentRepository.save(department);
        return convertToDto(department);
    }

    @Transactional
    public DepartmentDto updateDepartment(Long id, DepartmentRequest request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

        if (!department.getName().equals(request.getName()) &&
                departmentRepository.existsByName(request.getName())) {
            throw new ValidationException("Department with name '" + request.getName() + "' already exists");
        }

        department.setName(request.getName());
        department.setUpdatedAt(LocalDateTime.now());
        department = departmentRepository.save(department);
        return convertToDto(department);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));

        if (!department.getPersons().isEmpty()) {
            throw new ValidationException("Cannot delete department with existing persons");
        }

        departmentRepository.delete(department);
    }

    private DepartmentDto convertToDto(Department department) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setCreatedAt(department.getCreatedAt());
        dto.setUpdatedAt(department.getUpdatedAt());

        if (department.getPersons() != null) {
            List<PersonSimpleDto> personDtos = department.getPersons().stream()
                    .map(this::convertToSimpleDto)
                    .collect(Collectors.toList());
            dto.setPersons(personDtos);
        }

        return dto;
    }

    private PersonSimpleDto convertToSimpleDto(Person person) {
        PersonSimpleDto dto = new PersonSimpleDto();
        dto.setId(person.getId());
        dto.setName(person.getName());
        dto.setEmail(person.getEmail());
        return dto;
    }

    private DepartmentSimpleDto convertToSimpleDto(Department department) {
        DepartmentSimpleDto dto = new DepartmentSimpleDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        return dto;
    }
}