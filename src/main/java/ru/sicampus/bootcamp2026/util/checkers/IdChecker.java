package ru.sicampus.bootcamp2026.util.checkers;

import ru.sicampus.bootcamp2026.entity.Person;
import ru.sicampus.bootcamp2026.exception.PersonNotFoundException;
import ru.sicampus.bootcamp2026.repository.PersonRepository;

public class IdChecker {
    public static Person checkId(PersonRepository personRepository, Long id) {
        return personRepository
                .findById(id)
                .orElseThrow(PersonNotFoundException::new);
    }
}
