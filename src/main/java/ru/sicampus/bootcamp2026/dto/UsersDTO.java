package ru.sicampus.bootcamp2026.dto;
import lombok.Data;

@Data
public class UsersDTO {
    private Long id;
    private String email;
    private String fullName;
    private Integer age;
    private String jobTitle;
    private String department;
    private String contactInfo;
    private String avatarUrl;
}