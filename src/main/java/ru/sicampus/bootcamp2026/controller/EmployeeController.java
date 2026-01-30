package ru.sicampus.bootcamp2026.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dto.EmployeeDTO;
import ru.sicampus.bootcamp2026.dto.EmployeeEditDTO;
import ru.sicampus.bootcamp2026.dto.EmployeeRegisterDTO;
import ru.sicampus.bootcamp2026.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/register")
    @Operation(summary = "Register an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),

    })
    ResponseEntity<EmployeeDTO> registerEmployee(@RequestBody @Valid EmployeeRegisterDTO employeeRegisterDTO) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeRegisterDTO));
    }

    // TODO: When security is added
    @PostMapping("/login")
    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")

    })
    ResponseEntity<EmployeeDTO> login() {
        return ResponseEntity.ok(new EmployeeDTO());
    }

    // TODO: When security is added
    @PatchMapping()
    @Operation(summary = "Edit user's own profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),

    })
    public ResponseEntity<EmployeeDTO> patchVoid(@RequestBody @Valid EmployeeEditDTO employeeEditDTO) {
        return ResponseEntity.ok(new EmployeeDTO());
    }

    @GetMapping("/{username}")
    @Operation(summary = "Get a user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found"),

    })
    ResponseEntity<EmployeeDTO> getEmployeeByUsername(@PathVariable String username) {
        return ResponseEntity.ok(employeeService.getEmployeeByUsername(username));
    }

    @GetMapping("/all")
    @Operation(summary = "Search users or get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),

    })
    ResponseEntity<List<EmployeeDTO>> searchEmployees(@RequestParam(required = false) String search) {
        return ResponseEntity.ok(employeeService.searchEmployees(search));
    }

}
