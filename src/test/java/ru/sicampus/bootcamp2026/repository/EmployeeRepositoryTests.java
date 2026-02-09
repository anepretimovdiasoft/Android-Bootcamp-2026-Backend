package ru.sicampus.bootcamp2026.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import ru.sicampus.bootcamp2026.entity.Employee;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@ActiveProfiles("tests")
public class EmployeeRepositoryTests {
    @Autowired
    EmployeeRepository employeeRepository;



    @Test
    void findByUsername() {
        Employee employee = employeeRepository.findByUsername("indexzero");
        assertNotNull(employee);
        assertEquals("Калугин Олег Дмитриевич", employee.getName());
    }

    @Test
    void saveEmployee() {
        Employee employee = new Employee();
        employee.setName("Петров Петр Петрович");
        employee.setPosition("Разработчик");
        employee.setUsername("petrov_petr");
        employee.setEmail("okak@test.ru");
        employee.setPhoneNumber("+79997778833");
        employee.setPassword("HelloWorld12334");
        Employee emp = employeeRepository.save(employee);
        assertNotNull(emp);
        assertEquals("Петров Петр Петрович", employeeRepository.findById(emp.getId()).get().getName());
    }

    @Test
    void existsByUsernameOrEmailOrPhoneNumber() {
        boolean empExists = employeeRepository.existsByUsernameOrEmailOrPhoneNumber("indexzero", "me@indexzero.ru", "+79997778822");
        boolean empNotExists = employeeRepository.existsByUsernameOrEmailOrPhoneNumber("indexzero0", "mee@indexzero.ru", "+79991238822");
        assertTrue(empExists);
        assertFalse(empNotExists);
    }

    @Test
    void existsByEmailOrPhoneNumber() {
        boolean empExists = employeeRepository.existsByEmailOrPhoneNumber("me@indexzero.ru", "+79997778822");
        boolean empNotExists = employeeRepository.existsByEmailOrPhoneNumber("meeeee@isja.ru", "+79991238822");
        assertTrue(empExists);
        assertFalse(empNotExists);
    }

    @Test
    void findByNameContainsIgnoreCase() {
        List<Employee> employees = employeeRepository.findByNameContainsIgnoreCase("Калугин Олег Дмитриевич");
        assertFalse(employees.isEmpty());
        assertEquals("Калугин Олег Дмитриевич", employees.get(0).getName());
    }
}
