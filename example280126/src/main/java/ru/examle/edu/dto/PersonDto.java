package ru.examle.edu.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class PersonDto {
    private Long id;
    private String name;
    private String email;
    private DepartmentSimpleDto department;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

