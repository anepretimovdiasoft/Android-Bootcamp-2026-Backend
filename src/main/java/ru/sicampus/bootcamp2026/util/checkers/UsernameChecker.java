package ru.sicampus.bootcamp2026.util.checkers;

import ru.sicampus.bootcamp2026.exception.PersonAlreadyExistsException;
import ru.sicampus.bootcamp2026.repository.PersonRepository;

public class UsernameChecker {
    public static void checkUsername(PersonRepository personRepository, String username) {
        if (personRepository.findByUsername(username).isPresent()) {
            throw new PersonAlreadyExistsException();
        }
    }
}
