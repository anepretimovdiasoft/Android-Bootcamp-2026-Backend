package ru.sicampus.bootcamp2026.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.Dto.requst.CreatedEmployeeRequest;
import ru.sicampus.bootcamp2026.Dto.requst.GetEmployeeRequest;
import ru.sicampus.bootcamp2026.Dto.response.GetEmployeeResponse;
import ru.sicampus.bootcamp2026.Entity.Employee;
import ru.sicampus.bootcamp2026.Excepations.EmployeeFound;
import ru.sicampus.bootcamp2026.Excepations.EmployeeNotFound;
import ru.sicampus.bootcamp2026.Service.EmployeeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
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
    @PostMapping("/createdEm")
    public ResponseEntity<?> createdEmployee(@Valid @RequestBody CreatedEmployeeRequest dto) {
        try {
            employeeService.createdEmployee(dto);
        } catch (EmployeeFound e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
