package ru.examle.edu.service;

import ru.examle.edu.dto.PersonDTO;
import ru.examle.edu.enity.Person;

import java.util.List;

public interface PersonService {
    List<PersonDTO> getAllPersons();

    PersonDTO getPersonById(Long id);

    PersonDTO createPerson(PersonDTO dto);

    PersonDTO updatePerson(Long id, PersonDTO dto);

    void deletePerson(Long id);
}
