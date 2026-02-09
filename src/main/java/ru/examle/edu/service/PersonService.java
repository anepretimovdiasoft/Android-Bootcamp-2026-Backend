package ru.examle.edu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.examle.edu.dto.PersonDTO;
import ru.examle.edu.enity.Person;

public interface PersonService {
    Page<PersonDTO> getAllPersons(Pageable pageable);

    PersonDTO getPersonById(Long id);

    PersonDTO createPerson(PersonDTO dto);

    PersonDTO updatePerson(Long id, PersonDTO dto);

    void deletePerson(Long id);
}
