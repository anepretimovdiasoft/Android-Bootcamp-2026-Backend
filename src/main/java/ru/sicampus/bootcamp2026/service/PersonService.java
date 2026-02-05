package ru.sicampus.bootcamp2026.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.HashSet;
import ru.sicampus.bootcamp2026.api.dto.PersonRegisterRequest;
import ru.sicampus.bootcamp2026.api.dto.PersonUpdateRequest;
import ru.sicampus.bootcamp2026.api.error.BadRequestException;
import ru.sicampus.bootcamp2026.api.error.NotFoundException;
import ru.sicampus.bootcamp2026.domain.Authority;
import ru.sicampus.bootcamp2026.domain.Person;
import ru.sicampus.bootcamp2026.repository.AuthorityRepository;
import ru.sicampus.bootcamp2026.repository.PersonRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    public static final String ROLE_USER = "ROLE_USER";

    private final PersonRepository personRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonService(
        PersonRepository personRepository,
        AuthorityRepository authorityRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.personRepository = personRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Person> list() {
        return personRepository.findAll();
    }

    public Person get(Long id) {
        return personRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Person not found: " + id));
    }

    public Person getByEmail(String email) {
        return personRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException("Person not found: " + email));
    }

    public Person register(PersonRegisterRequest request) {
        if (personRepository.findByEmail(request.email()).isPresent()) {
            throw new BadRequestException("Email already registered: " + request.email());
        }

        Authority roleUser = authorityRepository.findByName(ROLE_USER)
            .orElseThrow(() -> new NotFoundException("Authority not found: " + ROLE_USER));

        Person person = new Person();
        person.setEmail(request.email());
        person.setPasswordHash(passwordEncoder.encode(request.password()));
        person.setFullName(request.fullName());
        person.setDepartment(request.department());
        person.setPosition(request.position());
        person.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        person.setAuthorities(new HashSet<>(List.of(roleUser)));

        return personRepository.save(person);
    }

    public Person update(Long id, PersonUpdateRequest request) {
        Person person = get(id);
        if (request.fullName() != null) {
            person.setFullName(request.fullName());
        }
        if (request.department() != null) {
            person.setDepartment(request.department());
        }
        if (request.position() != null) {
            person.setPosition(request.position());
        }
        if (request.password() != null && !request.password().isBlank()) {
            person.setPasswordHash(passwordEncoder.encode(request.password()));
        }

        return personRepository.save(person);
    }

    public void delete(Long id) {
        if (!personRepository.existsById(id)) {
            throw new NotFoundException("Person not found: " + id);
        }
        personRepository.deleteById(id);
    }
}
