package ru.sicampus.bootcamp2026.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.Dto.requst.createdEmployeeRequest;
import ru.sicampus.bootcamp2026.Entity.Employee;
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
    public ResponseEntity<?> getEmployee(@RequestBody Map<String,String> body){
        try{
            String name=body.get("name");
            Employee employee=employeeService.getEmployee(name);
            return ResponseEntity.ok(employee);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (EmployeeNotFound e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/Employees")
    public ResponseEntity<?> getEmployees(){
        try{
            List<Employee> result=employeeService.getEmployees();
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PostMapping("/createdEm")
    public void createdEmployee(@Valid @RequestBody createdEmployeeRequest dto) {
        try {
        } catch (EmployeeNotFound e) {
        }
    }
}
