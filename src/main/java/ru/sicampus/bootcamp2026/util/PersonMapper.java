package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.PersonDTO;
import ru.sicampus.bootcamp2026.dto.PersonRegisterDTO;
import ru.sicampus.bootcamp2026.entity.Authority;
import ru.sicampus.bootcamp2026.entity.Department;
import ru.sicampus.bootcamp2026.entity.Person;
import ru.sicampus.bootcamp2026.exception.PersonNotFoundException;
import ru.sicampus.bootcamp2026.repository.PersonRepository;

import java.time.LocalDateTime;
import java.util.Set;

@UtilityClass
public class PersonMapper {
    public PersonDTO convertToDto(Person Person) {
        PersonDTO PersonDTO = new PersonDTO();
        PersonDTO.setId(Person.getId());
        PersonDTO.setName(Person.getName());
        PersonDTO.setUsername(Person.getUsername());
        PersonDTO.setEmail(Person.getEmail());
        PersonDTO.setPhotoUrl(Person.getPhotoUrl());
        PersonDTO.setDepartmentName(Person.getDepartment().getName());
        PersonDTO.setCreatedAt(Person.getCreatedAt());
        return PersonDTO;
    }
    public Person convertToEntity(
            PersonRegisterDTO dto,
            Authority authority,
            Department department,
            String password
    ) {
        Person person = new Person();
        person.setName(dto.getName());
        person.setUsername(dto.getUsername());
        person.setEmail(dto.getEmail());
        person.setDepartment(department);
        person.setPassword(password);
        person.setAuthorities(Set.of(authority));
        person.setCreatedAt(LocalDateTime.now());
        return person;
    }
    public Person convertToEntity(
            Long id,
            PersonDTO dto,
            Department department,
            PersonRepository personRepository
    ) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        person.setName(dto.getName());
        person.setUsername(dto.getUsername());
        person.setEmail(dto.getEmail());
        person.setPhotoUrl(dto.getPhotoUrl());
        person.setDepartment(department);
        person.setCreatedAt(LocalDateTime.now());
        return person;
    }
}
