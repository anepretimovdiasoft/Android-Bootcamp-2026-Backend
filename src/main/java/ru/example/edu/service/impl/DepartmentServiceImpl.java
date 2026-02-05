package ru.example.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.edu.dto.DepartmentDTO;
import ru.example.edu.entity.Department;
import ru.example.edu.exception.DepartmentNotFoundException;
import ru.example.edu.repository.DepartmentRepository;
import ru.example.edu.service.DepartmentService;
import ru.example.edu.util.DepartmentMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream().map(DepartmentMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        return departmentRepository.findById(id).map(DepartmentMapper::convertToDto).orElseThrow(() -> new DepartmentNotFoundException("Department not found!"));
    }

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO dto) {
        Department department = new Department();
        department.setName(dto.getName());
        return DepartmentMapper.convertToDto(departmentRepository.save(department));
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO dto) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException("Department not found!"));
        department.setName(dto.getName());
        return DepartmentMapper.convertToDto(departmentRepository.save(department));
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException("Department not found!"));
        departmentRepository.deleteById(id);
    }
}
