package ru.sicampus.bootcamp2026.Dto.response.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class UpdateEmployeeResponse {
    private String token;
    public UpdateEmployeeResponse(String token){
        this.token=token;
    }

    public String getToken() {
        return token;
    }
}
