package ru.sicampus.bootcamp2026.Service;

import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Dto.requst.Employee.*;
import ru.sicampus.bootcamp2026.Dto.response.Employee.CreatedEmployeeResponse;
import ru.sicampus.bootcamp2026.Dto.response.Employee.GetEmployeeResponse;
import ru.sicampus.bootcamp2026.Dto.response.Employee.GetEmployeesResponse;
import ru.sicampus.bootcamp2026.Dto.response.Employee.UpdateEmployeeResponse;

import java.util.List;

@Service
public interface EmployeeService {
    GetEmployeeResponse getEmployee(GetEmployeeRequest dto);
    GetEmployeesResponse getEmployees(int page, int size);
     CreatedEmployeeResponse createdEmployee(CreatedEmployeeRequest dto);
     Boolean AuthorizedEmployee(GetAuthorizedEmployeeRequest dto);
    UpdateEmployeeResponse updateEmployee(GetEmployeeUpdateRequest dto);
    GetYouResponse getYou();
}
