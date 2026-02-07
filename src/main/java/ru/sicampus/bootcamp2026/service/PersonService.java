package ru.sicampus.bootcamp2026.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.sicampus.bootcamp2026.dto.PersonDTO;
import ru.sicampus.bootcamp2026.dto.PersonRegisterDTO;
import ru.sicampus.bootcamp2026.entity.Authority;
import ru.sicampus.bootcamp2026.entity.Department;
import ru.sicampus.bootcamp2026.entity.Person;

import java.util.List;

public interface PersonService {
    Person convertRegisterToEntity(
            PersonRegisterDTO dto,
            Authority authority,
            Department department,
            String password
    );

    String encodePassword(String password);

    List<PersonDTO> getAllPersons();

    PersonDTO getPersonById(Long id);

    PersonDTO createPerson(PersonRegisterDTO dto);

    PersonDTO updatePerson(Long id, PersonDTO dto);

    void deletePerson(Long id);

    PersonDTO getPersonByUsername(String username);

    Page<PersonDTO> getAllPersonPaginated(Pageable page);
}
