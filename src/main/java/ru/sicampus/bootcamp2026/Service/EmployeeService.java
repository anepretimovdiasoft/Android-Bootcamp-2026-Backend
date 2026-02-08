package ru.sicampus.bootcamp2026.Service;

import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Dto.requst.Employee.*;
import ru.sicampus.bootcamp2026.Dto.response.Employee.*;

@Service
public interface EmployeeService {
    GetEmployeeResponse getEmployee(GetEmployeeRequest dto);
    GetEmployeesResponse getEmployees(int page, int size);
     CreatedEmployeeResponse createdEmployee(CreatedEmployeeRequest dto);
     Boolean AuthorizedEmployee(GetAuthorizedEmployeeRequest dto);
    UpdateEmployeeResponse updateEmployee(GetEmployeeUpdateRequest dto);
    GetYouResponse getYou();
}
