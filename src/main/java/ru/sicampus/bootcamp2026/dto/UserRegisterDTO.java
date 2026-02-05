package ru.sicampus.bootcamp2026.dto;
import lombok.Data;

@Data
public class UserRegisterDTO {
    private String fullName;
    private String email;
    private Integer age;
    private String password;
    private String passwordConfirm;
    private String jobTitle;
    private String department;
}