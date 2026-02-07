package ru.sicampus.bootcamp2026.Dto.response.Employee;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class GetEmployeesResponse {
    private List<Map<String, Object>> employees;

    public List<Map<String, Object>> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Map<String, Object>> employees) {
        this.employees = employees;
    }
}
