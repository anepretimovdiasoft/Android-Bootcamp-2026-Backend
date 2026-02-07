package ru.sicampus.bootcamp2026.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.Dto.requst.Employee.CreatedEmployeeRequest;
import ru.sicampus.bootcamp2026.Dto.requst.Employee.GetAuthorizedEmployeeRequest;
import ru.sicampus.bootcamp2026.Dto.requst.Employee.GetEmployeeRequest;
import ru.sicampus.bootcamp2026.Dto.requst.Employee.GetEmployeeUpdateRequest;
import ru.sicampus.bootcamp2026.Dto.response.Employee.AuthorizedEmployeeResponse;
import ru.sicampus.bootcamp2026.Dto.response.Employee.CreatedEmployeeResponse;
import ru.sicampus.bootcamp2026.Dto.response.Employee.GetEmployeeResponse;
import ru.sicampus.bootcamp2026.Dto.response.Employee.GetEmployeesResponse;
import ru.sicampus.bootcamp2026.Entity.Employee;
import ru.sicampus.bootcamp2026.Excepations.EmployeeFound;
import ru.sicampus.bootcamp2026.Excepations.EmployeeNotFound;
import ru.sicampus.bootcamp2026.Service.EmployeeService;
import ru.sicampus.bootcamp2026.Service.TokenAuthService;

import java.util.List;

@RestController
@RequestMapping("/api/Employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TokenAuthService tokenAuthService;

    @GetMapping("/Employee")
    public ResponseEntity<?> getEmployee(@Valid @RequestBody GetEmployeeRequest dto){
        try{
            return ResponseEntity.ok(employeeService.getEmployee(dto));
        }catch(EmployeeNotFound e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping( "/Employees")
    public ResponseEntity<?> getEmployees(){
        try{
            GetEmployeesResponse result=employeeService.getEmployees();
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PostMapping("/auth")
    public ResponseEntity<?> AuthorizedEmployee(@Valid @RequestBody GetAuthorizedEmployeeRequest dto){
        boolean valid = employeeService.AuthorizedEmployee(dto);
        if (!valid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = tokenAuthService.createToken(dto.getEmail());
        return ResponseEntity.ok(new AuthorizedEmployeeResponse(token));
    }

    @PostMapping("/createdEm")
    public ResponseEntity<CreatedEmployeeResponse> register(
            @Valid @RequestBody CreatedEmployeeRequest dto) {

        try {
            CreatedEmployeeResponse employee = employeeService.createdEmployee(dto);
            String token = tokenAuthService.createToken(employee.getToken());
            return ResponseEntity.ok(new CreatedEmployeeResponse(token));
        } catch (EmployeeFound e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @PutMapping("/updateEmployee")
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody GetEmployeeUpdateRequest dto){
            String token=employeeService.updateEmployee(dto).getToken();
            return ResponseEntity.ok(token);
    }
}
