package ru.sicampus.bootcamp2026.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String photoUrl;
    private String position;
    private List<String> roles;
}