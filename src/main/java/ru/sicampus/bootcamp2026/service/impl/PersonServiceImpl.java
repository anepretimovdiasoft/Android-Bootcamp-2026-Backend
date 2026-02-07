package ru.sicampus.bootcamp2026.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sicampus.bootcamp2026.dto.PersonDTO;
import ru.sicampus.bootcamp2026.dto.PersonRegisterDTO;
import ru.sicampus.bootcamp2026.entity.Authority;
import ru.sicampus.bootcamp2026.entity.Department;
import ru.sicampus.bootcamp2026.entity.Person;
import ru.sicampus.bootcamp2026.exception.DepartmentNotFoundException;
import ru.sicampus.bootcamp2026.exception.PersonAlreadyExistsException;
import ru.sicampus.bootcamp2026.exception.PersonNotFoundException;
import ru.sicampus.bootcamp2026.repository.AuthorityRepository;
import ru.sicampus.bootcamp2026.repository.DepartmentRepository;
import ru.sicampus.bootcamp2026.repository.PersonRepository;
import ru.sicampus.bootcamp2026.service.PersonService;
import ru.sicampus.bootcamp2026.util.PersonMapper;
import ru.sicampus.bootcamp2026.util.checkers.AuthorityChecker;
import ru.sicampus.bootcamp2026.util.checkers.DepartmentChecker;
import ru.sicampus.bootcamp2026.util.checkers.UsernameChecker;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final DepartmentRepository departmentRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Person convertRegisterToEntity(
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
        return person;
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public List<PersonDTO> getAllPersons() {
        return personRepository.findAll().stream().map(PersonMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public PersonDTO getPersonById(Long id) {
        return personRepository.findById(id).map(PersonMapper::convertToDto).orElseThrow(PersonNotFoundException::new);
    }

    @Override
    public PersonDTO createPerson(PersonRegisterDTO dto) {

        UsernameChecker.checkUsername(personRepository, dto.getUsername());
        Department department = DepartmentChecker.checkDepartment(departmentRepository, dto.getDepartmentName());
        Authority authority = AuthorityChecker.checkAuthority(authorityRepository, "ROLE_USER");
        String encodedPassword = encodePassword(dto.getPassword());

        Person person = convertRegisterToEntity(dto, authority, department, encodedPassword);

        return PersonMapper.convertToDto(personRepository.save(person));
    }

    @Override
    public PersonDTO updatePerson(Long id, PersonDTO dto) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

        UsernameChecker.checkUsername(personRepository, dto.getUsername());
        Department department = DepartmentChecker.checkDepartment(departmentRepository, dto.getDepartmentName());

        person.setName(dto.getName());
        person.setUsername(dto.getUsername());
        person.setEmail(dto.getEmail());
        person.setPhotoUrl(dto.getPhotoUrl());
        person.setDepartment(department);

        return PersonMapper.convertToDto(personRepository.save(person));
    }

    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public PersonDTO getPersonByUsername(String username) {
        Optional<Person> optionalPerson = personRepository.findByUsername(username);

        if (optionalPerson.isEmpty()) {
            throw new PersonNotFoundException("Person with username" + username + "name not found!");
        }

        return PersonMapper.convertToDto(optionalPerson.get());
    }

    @Override
    public Page<PersonDTO> getAllPersonPaginated(Pageable pageable) {
        return personRepository.findAll(pageable).map(PersonMapper::convertToDto);
    }
}
