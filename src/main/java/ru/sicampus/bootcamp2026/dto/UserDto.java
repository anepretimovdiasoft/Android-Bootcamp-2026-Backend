package ru.sicampus.bootcamp2026.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private String position;
}
