package ru.sicampus.bootcamp2026.Service;

import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Dto.requst.createdEmployeeRequest;
import ru.sicampus.bootcamp2026.Entity.Employee;

import java.util.List;

@Service
public interface EmployeeService {
    Employee getEmployee(String name);
    List<Employee> getEmployees();
     void createdEmployee(createdEmployeeRequest dto);
}
