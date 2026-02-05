package ru.sicampus.bootcamp2026.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateDto {
    private String email;
    private String username;
    private String position;
    private String password;
}
