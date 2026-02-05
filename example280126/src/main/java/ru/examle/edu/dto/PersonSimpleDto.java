package ru.examle.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonSimpleDto {
    private Long id;
    private String name;
    private String email;
}
