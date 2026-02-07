package ru.sicampus.bootcamp2026.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.sicampus.bootcamp2026.dto.EmployeeDTO;
import ru.sicampus.bootcamp2026.dto.EmployeeEditDTO;
import ru.sicampus.bootcamp2026.dto.EmployeeRegisterDTO;
import ru.sicampus.bootcamp2026.entity.Employee;
import ru.sicampus.bootcamp2026.exception.EmployeeAlreadyExistsException;
import ru.sicampus.bootcamp2026.exception.EmployeeNotFoundException;
import ru.sicampus.bootcamp2026.repository.EmployeeRepository;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("tests")
@SpringBootTest
@Transactional
public class EmployeeServiceTests {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void createEmployee() {
        EmployeeRegisterDTO emp = new EmployeeRegisterDTO();
        emp.setName("Морозов Михаил Иванович");
        emp.setPosition("Тестировщик");
        emp.setUsername("test_mihail");
        emp.setEmail("tests@tests.ts");
        emp.setPhoneNumber("+79997778833");
        emp.setPassword("1234567812");

        employeeService.createEmployee(emp);
        Employee empRegistered = employeeRepository.findByUsername("test_mihail");
        assertNotNull(empRegistered);
        assertEquals("Морозов Михаил Иванович", empRegistered.getName());
    }

    @Test
    void createEmployeeAlreadyExists() {
        EmployeeRegisterDTO emp = new EmployeeRegisterDTO();
        emp.setName("Морозов Михаил Иванович");
        emp.setPosition("Тестировщик");
        emp.setUsername("test_mihail");
        emp.setEmail("tests@tests.ts");
        emp.setPhoneNumber("+79997778833");
        emp.setPassword("1234567812");

        EmployeeRegisterDTO empExs = new EmployeeRegisterDTO();
        empExs.setName("Морозов Михаил Иванович");
        empExs.setPosition("Бекендер");
        empExs.setUsername("test_mihail");
        empExs.setEmail("tests@tests.ts");
        empExs.setPhoneNumber("+79997778833");
        empExs.setPassword("1234567812");

        employeeService.createEmployee(emp);

        Exception exception = assertThrows(EmployeeAlreadyExistsException.class, () -> employeeService.createEmployee(empExs));
        assertTrue(exception.getMessage().contains("Employee with the same credentials is already registered"));
    }

    @Test
    void getByUsername() {
        EmployeeDTO emp = employeeService.getEmployeeByUsername("andrey_limasov");
        assertNotNull(emp);
        assertEquals("Андрей Лимасов", emp.getName());
    }

    @Test
    void getByUsernameNotFound() {
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeByUsername("zzzzzzzzzzzzzzzz"));
        assertTrue(exception.getMessage().contains("Employee Not Found"));
    }

    @Test
    void editEmployee() {
        EmployeeEditDTO emp = new EmployeeEditDTO();
        emp.setName("Andrey Limasov");
        emp.setPosition("Android dev");
        emp.setEmail("test@mail.com");
        emp.setPhoneNumber("+71234567890");
        emp.setPhotoUrl("https://photo.com/image.jpg");

        EmployeeDTO empEdited = employeeService.editEmployee(emp, "andrey_limasov");
        assertNotNull(empEdited);
        assertEquals("Andrey Limasov", empEdited.getName());
    }

    @Test
    void editEmployeeConflict() {
        EmployeeEditDTO emp = new EmployeeEditDTO();
        emp.setName("Andrey Limasov");
        emp.setPosition("Android dev");
        emp.setEmail("max_naumov@company.ru");
        emp.setPhoneNumber("+79869830631");
        emp.setPhotoUrl("https://photo.com/image.jpg");

        Exception exception = assertThrows(EmployeeAlreadyExistsException.class, () -> employeeService.editEmployee(emp, "andrey_limasov"));
        assertTrue(exception.getMessage().contains("Employee with the same credentials is already registered"));
    }
}
