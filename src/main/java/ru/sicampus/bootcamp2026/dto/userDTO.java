package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

@Data
public class userDTO {
    private Long id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String photoUrl;
    private String position;
}