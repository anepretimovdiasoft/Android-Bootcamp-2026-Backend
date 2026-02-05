package ru.sicampus.bootcamp2026.dto;


import lombok.Data;

@Data
public class EmployeeDTO {
    private Long Id;
    private String name;
    private String position;
    private String username;
    private String email;
    private String phoneNumber;
    private String photoUrl;
}