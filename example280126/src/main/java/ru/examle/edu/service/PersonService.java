package ru.examle.edu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.examle.edu.dto.*;
import ru.examle.edu.entity.Department;
import ru.examle.edu.entity.Person;
import ru.examle.edu.exception.ResourceNotFoundException;
import ru.examle.edu.exception.ValidationException;
import ru.examle.edu.repository.DepartmentRepository;
import ru.examle.edu.repository.PersonRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    public List<PersonDto> getAllPersons() {
        return personRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PersonDto getPersonById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
        return convertToDto(person);
    }

    @Transactional
    public PersonDto createPerson(PersonRequest request) {
        if (personRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Person with email '" + request.getEmail() + "' already exists");
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.getDepartmentId()));

        Person person = new Person();
        person.setName(request.getName());
        person.setEmail(request.getEmail());
        person.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        person.setDepartment(department);
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());

        person = personRepository.save(person);
        return convertToDto(person);
    }

    @Transactional
    public PersonDto updatePerson(Long id, PersonRequest request) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        if (!person.getEmail().equals(request.getEmail()) &&
                personRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Person with email '" + request.getEmail() + "' already exists");
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.getDepartmentId()));

        person.setName(request.getName());
        person.setEmail(request.getEmail());
        person.setDepartment(department);
        person.setUpdatedAt(LocalDateTime.now());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            person.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        person = personRepository.save(person);
        return convertToDto(person);
    }

    @Transactional
    public void deletePerson(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        if (!person.getOrganizedMeetings().isEmpty()) {
            throw new ValidationException("Cannot delete person who is organizer of meetings");
        }

        if (!person.getInvitations().isEmpty()) {
            throw new ValidationException("Cannot delete person with active invitations");
        }

        personRepository.delete(person);
    }

    private PersonDto convertToDto(Person person) {
        PersonDto dto = new PersonDto();
        dto.setId(person.getId());
        dto.setName(person.getName());
        dto.setEmail(person.getEmail());
        dto.setCreatedAt(person.getCreatedAt());
        dto.setUpdatedAt(person.getUpdatedAt());

        if (person.getDepartment() != null) {
            DepartmentSimpleDto departmentDto = new DepartmentSimpleDto();
            departmentDto.setId(person.getDepartment().getId());
            departmentDto.setName(person.getDepartment().getName());
            dto.setDepartment(departmentDto);
        }

        return dto;
    }

    private PersonSimpleDto convertToSimpleDto(Person person) {
        PersonSimpleDto dto = new PersonSimpleDto();
        dto.setId(person.getId());
        dto.setName(person.getName());
        dto.setEmail(person.getEmail());
        return dto;
    }
}