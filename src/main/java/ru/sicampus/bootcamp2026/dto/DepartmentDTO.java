package ru.sicampus.bootcamp2026.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentDTO {
    private Long id;
    private String name;
}
