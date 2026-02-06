package ru.sicampus.bootcamp2026.Dto.response.Employee;

public class CreatedEmployeeResponse {
    private final String token;

    public CreatedEmployeeResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }


}
