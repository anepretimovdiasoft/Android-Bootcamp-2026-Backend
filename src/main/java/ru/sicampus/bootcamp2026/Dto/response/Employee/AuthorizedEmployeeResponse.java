package ru.sicampus.bootcamp2026.Dto.response.Employee;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthorizedEmployeeResponse {
    private String token;

    public AuthorizedEmployeeResponse(String token) {
        this.token=token;
    }

    public String getToken() {
        return token;
    }
}
