package ru.sicampus.bootcamp2026.Dto.response.Employee;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor
public class GetEmployeesResponse {
    private List<Map<String, Object>> employees;

    public void setEmployees(List<Map<String, Object>> employees) {
        this.employees = employees;
    }
}
