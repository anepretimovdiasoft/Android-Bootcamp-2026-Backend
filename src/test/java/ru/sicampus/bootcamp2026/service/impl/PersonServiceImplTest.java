package ru.sicampus.bootcamp2026.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sicampus.bootcamp2026.dto.PersonDTO;
import ru.sicampus.bootcamp2026.dto.PersonRegisterDTO;
import ru.sicampus.bootcamp2026.entity.Authority;
import ru.sicampus.bootcamp2026.entity.Department;
import ru.sicampus.bootcamp2026.entity.Person;
import ru.sicampus.bootcamp2026.exception.PersonAlreadyExistsException;
import ru.sicampus.bootcamp2026.repository.AuthorityRepository;
import ru.sicampus.bootcamp2026.repository.DepartmentRepository;
import ru.sicampus.bootcamp2026.repository.PersonRepository;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    PersonRepository personRepo;
    @Mock
    DepartmentRepository departmentRepo;
    @Mock
    AuthorityRepository authorityRepo;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    PersonServiceImpl service;

    private Department itDepartment;
    private Authority userRole;
    private PersonRegisterDTO testRegisterDto;

    @BeforeEach
    void setUp() {
        itDepartment = new Department();
        itDepartment.setId(1L);
        itDepartment.setName("IT");

        userRole = new Authority();
        userRole.setId(1L);
        userRole.setAuthority("ROLE_USER");

        testRegisterDto = new PersonRegisterDTO();
        testRegisterDto.setUsername("testuser");
        testRegisterDto.setEmail("test@mail.com");
        testRegisterDto.setName("Test User");
        testRegisterDto.setPassword("password123");
        testRegisterDto.setDepartmentName("IT");
    }

    @Test
    void createPerson_success() {
        when(personRepo.findByUsername("testuser")).thenReturn(Optional.empty());
        when(departmentRepo.findByName("IT")).thenReturn(Optional.of(itDepartment));
        when(authorityRepo.findByAuthority("ROLE_USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        // Мок для сохранения
        Person savedPerson = new Person();
        savedPerson.setId(1L);
        savedPerson.setUsername("testuser");
        savedPerson.setEmail("test@mail.com");
        savedPerson.setName("Test User");
        savedPerson.setDepartment(itDepartment);
        savedPerson.setAuthorities(Set.of(userRole));

        when(personRepo.save(any(Person.class))).thenReturn(savedPerson);

        // When: вызываем тестируемый метод
        PersonDTO result = service.createPerson(testRegisterDto);

        // Then: проверяем
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@mail.com", result.getEmail());
        assertEquals("IT", result.getDepartmentName());
    }

    @Test
    void createPerson_usernameAlreadyExists_throwsException() {
        // Given: пользователь с таким username уже есть
        Person existingPerson = new Person();
        existingPerson.setUsername("testuser");

        when(personRepo.findByUsername("testuser")).thenReturn(Optional.of(existingPerson));

        // When & Then: должен выбросить исключение
        assertThrows(PersonAlreadyExistsException.class,
                () -> service.createPerson(testRegisterDto));

        // И не должен вызывать save()
        verify(personRepo, never()).save(any());
    }

    @Test
    void createPerson_departmentNotFound_throwsException() {
        // Given: username свободен, но департамента нет
        when(personRepo.findByUsername("testuser")).thenReturn(Optional.empty());
        when(departmentRepo.findByName("IT")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(DepartmentNotFoundException.class,
                () -> service.createPerson(testRegisterDto));

        verify(personRepo, never()).save(any());
    }
}
