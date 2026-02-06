package ru.sicampus.bootcamp2026.Dto.requst.Employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GetAuthorizedEmployeeRequest {
    @NotBlank
    @NotNull
    @Email
    private String mail;
    @NotNull
    @NotBlank
    @Size(min =8)
    private String password;

    public GetAuthorizedEmployeeRequest(String token) {
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return mail;
    }
}
