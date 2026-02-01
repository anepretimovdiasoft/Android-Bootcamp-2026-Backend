package ru.example.edu.util;

import lombok.experimental.UtilityClass;
import ru.example.edu.dto.DepartmentDTO;
import ru.example.edu.entity.Department;

@UtilityClass
public class DepartmentMapper {
    public DepartmentDTO convertToDto(Department department) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(department.getId());
        departmentDTO.setName(department.getName());
        return departmentDTO;
    }
}
