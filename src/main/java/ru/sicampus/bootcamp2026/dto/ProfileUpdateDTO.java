package ru.sicampus.bootcamp2026.dto;
import lombok.Data;

@Data
public class ProfileUpdateDTO {
    private String fullName;
    private String contactInfo;
    private String avatarUrl;
}