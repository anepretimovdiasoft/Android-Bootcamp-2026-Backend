package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.PersonDTO;
import ru.sicampus.bootcamp2026.entity.Person;

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
}
