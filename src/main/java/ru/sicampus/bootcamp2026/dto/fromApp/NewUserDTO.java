package ru.sicampus.bootcamp2026.dto.fromApp;

import lombok.Data;

@Data
public class NewUserDTO {
    private String email;
    private String password;
    private String fullName;
//    private String photoUrl;
}
