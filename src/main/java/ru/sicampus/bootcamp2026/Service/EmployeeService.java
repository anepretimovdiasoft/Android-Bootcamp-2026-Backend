package ru.sicampus.bootcamp2026.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.Dto.requst.CreatedEmployeeRequest;
import ru.sicampus.bootcamp2026.Dto.requst.GetAuthorizedEmployeeRequest;
import ru.sicampus.bootcamp2026.Dto.requst.GetEmployeeRequest;
import ru.sicampus.bootcamp2026.Dto.requst.GetEmployeeUpdateRequest;
import ru.sicampus.bootcamp2026.Dto.response.CreatedEmployeeResponse;
import ru.sicampus.bootcamp2026.Dto.response.GetEmployeeResponse;

import java.util.List;

@Service
public interface EmployeeService {
    GetEmployeeResponse getEmployee(GetEmployeeRequest dto);
    List<GetEmployeeResponse> getEmployees();
     CreatedEmployeeResponse createdEmployee(CreatedEmployeeRequest dto);
    String AuthorizedEmployee(GetAuthorizedEmployeeRequest dto);
    void updateEmployee(GetEmployeeUpdateRequest dto);
}
