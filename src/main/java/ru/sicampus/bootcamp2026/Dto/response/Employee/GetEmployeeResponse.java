package ru.sicampus.bootcamp2026.Dto.response.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetEmployeeResponse {
    private List<Map<String,Object>> employees;

    public void setEmployees(List<Map<String, Object>> employees) {
        this.employees = employees;
    }
}
