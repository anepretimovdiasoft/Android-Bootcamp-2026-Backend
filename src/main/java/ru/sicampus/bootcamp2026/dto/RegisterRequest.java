package ru.sicampus.bootcamp2026.dto;


import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    String email;
    String password;
    String firstName;
    String lastName;
    String position;
    String department;
}
