package ru.sicampus.bootcamp2026.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.Dto.requst.Employee.CreatedEmployeeRequest;
import ru.sicampus.bootcamp2026.Dto.requst.Employee.GetAuthorizedEmployeeRequest;
import ru.sicampus.bootcamp2026.Dto.requst.Employee.GetEmployeeRequest;
import ru.sicampus.bootcamp2026.Dto.requst.Employee.GetEmployeeUpdateRequest;
import ru.sicampus.bootcamp2026.Dto.response.Employee.GetEmployeeResponse;
import ru.sicampus.bootcamp2026.Excepations.EmployeeFound;
import ru.sicampus.bootcamp2026.Excepations.EmployeeNotFound;
import ru.sicampus.bootcamp2026.Service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/Employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
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
    @GetMapping("/Employees")
    public ResponseEntity<?> getEmployees(){
        try{
            List<GetEmployeeResponse> result=employeeService.getEmployees();
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/auth")
    public ResponseEntity<?> AuthorizedEmployee(@Valid @RequestBody GetAuthorizedEmployeeRequest dto){
        try{
            String s= employeeService.AuthorizedEmployee(dto);
            return ResponseEntity.ok(s);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PostMapping("/createdEm")
    public ResponseEntity<?> createdEmployee(@Valid @RequestBody CreatedEmployeeRequest dto) {
        try {
            String token=employeeService.createdEmployee(dto).getToken();
            return ResponseEntity.ok(token);
        } catch (EmployeeFound e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    @PutMapping("/updateEmployee")
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody GetEmployeeUpdateRequest dto){
            String token=employeeService.updateEmployee(dto).getToken();
            return ResponseEntity.ok(token);
    }
}
