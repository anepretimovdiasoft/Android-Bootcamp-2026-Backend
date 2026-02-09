package ru.example.edu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.example.edu.dto.PersonRegisterDTO;
import ru.example.edu.dto.PersonShortDTO;
import ru.example.edu.dto.PersonWithInvitesDTO;

import java.util.List;

public interface PersonService {
    List<PersonWithInvitesDTO> getAllPersons();

    PersonWithInvitesDTO getPersonById(Long id);

    PersonWithInvitesDTO getPersonByLogin(String login);

    PersonShortDTO createPerson(PersonRegisterDTO dto);

    PersonWithInvitesDTO updatePerson(Long id, PersonShortDTO dto);

    void deletePerson(Long id);

    Page<PersonWithInvitesDTO> getAllPersonPaginated(Pageable pageable);

    List<PersonShortDTO> getPersonWithNameLike(String likeName);
}
