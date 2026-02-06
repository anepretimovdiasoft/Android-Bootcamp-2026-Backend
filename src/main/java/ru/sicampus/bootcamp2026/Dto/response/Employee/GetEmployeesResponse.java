package ru.sicampus.bootcamp2026.Dto.response.Employee;

import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
public class GetEmployeesResponse {
    private List<GetEmployeeResponse> employees;

    public void setEmployees(List<GetEmployeeResponse> employees) {
        this.employees = employees;
    }
}
