package ru.sicampus.bootcamp2026.Dto.response.Employee;

public class UpdateEmployeeResponse {
    private String token;
    public UpdateEmployeeResponse(String token){
        this.token=token;
    }

    public String getToken() {
        return token;
    }
}
