package ru.example.edu.service;

import ru.example.edu.dto.PersonWithInvitesDTO;

import java.util.List;

public interface PersonService {
    List<PersonWithInvitesDTO> getAllPersons();

    PersonWithInvitesDTO getPersonByUd(Long id);

    PersonWithInvitesDTO createPerson(PersonWithInvitesDTO dto);

    PersonWithInvitesDTO updatePerson(Long id, PersonWithInvitesDTO dto);

    void deletePerson(Long id);
}
