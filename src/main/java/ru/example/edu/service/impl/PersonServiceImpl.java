package ru.example.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.example.edu.dto.PersonRegisterDTO;
import ru.example.edu.dto.PersonShortDTO;
import ru.example.edu.dto.PersonWithInvitesDTO;
import ru.example.edu.entity.Authority;
import ru.example.edu.entity.Department;
import ru.example.edu.entity.Person;
import ru.example.edu.exception.DepartmentNotFoundException;
import ru.example.edu.exception.PersonAlreadyExistsException;
import ru.example.edu.exception.PersonNotFoundException;
import ru.example.edu.repository.AuthorityRepository;
import ru.example.edu.repository.DepartmentRepository;
import ru.example.edu.repository.PersonRepository;
import ru.example.edu.service.PersonService;
import ru.example.edu.util.PersonMapper;

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
    public List<PersonWithInvitesDTO> getAllPersons() {
        return personRepository.findAll().stream().map(PersonMapper::convertToDtoWithInvites).collect(Collectors.toList());
    }

    @Override
    public PersonWithInvitesDTO getPersonById(Long id) {
        return personRepository.findById(id).map(PersonMapper::convertToDtoWithInvites).orElseThrow(() -> new PersonNotFoundException("Person not found!"));
    }

    @Override
    public PersonWithInvitesDTO getPersonByLogin(String login) {
        Optional<Person> optionalPerson = personRepository.findByLogin(login);

        if (optionalPerson.isEmpty()) {
            throw new PersonNotFoundException("Person with login " + login + " not found!");
        }

        return PersonMapper.convertToDtoWithInvites(optionalPerson.get());
    }

    @Override
    public PersonShortDTO createPerson(PersonRegisterDTO dto) {
        if (personRepository.findByLogin(dto.getLogin()).isPresent()) {
            throw new PersonAlreadyExistsException("Login already exists!");
        }
        Optional<Department> optionalDepartment = departmentRepository.findByName(dto.getDepartmentName());
        if (optionalDepartment.isEmpty()) {
            throw new DepartmentNotFoundException("Department not found!");
        }

        Optional<Authority> roleUser = authorityRepository.findByAuthority("ROLE_USER");
        if (roleUser.isEmpty()) {
            throw new RuntimeException("Authority not found!");
        }
        Person person = new Person();
        person.setName(dto.getName());
        person.setLogin(dto.getLogin());
        person.setDepartment(optionalDepartment.get());
        person.setPassword(passwordEncoder.encode(dto.getPassword()));
        person.setAuthorities(Set.of(roleUser.get()));

        return PersonMapper.convertToShortDto(personRepository.save(person));
    }

    @Override
    public PersonWithInvitesDTO updatePerson(Long id, PersonShortDTO dto) {
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found!"));

        Optional<Person> already = personRepository.findByLogin(dto.getLogin());

        if (already.isPresent() && already.get().getId() != dto.getId()) {
            throw new PersonAlreadyExistsException("Login already exists!");
        }

        person.setName(dto.getName());
        person.setPhotoUrl(dto.getPhotoUrl());
        person.setLogin(dto.getLogin());

        Optional<Department> department = departmentRepository.findByName(dto.getDepartmentName());
        department.ifPresent(person::setDepartment);

        return PersonMapper.convertToDtoWithInvites(personRepository.save(person));
    }

    @Override
    public void deletePerson(Long id) {
        personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found!"));
        personRepository.deleteById(id);
    }

    @Override
    public Page<PersonWithInvitesDTO> getAllPersonPaginated(Pageable pageable) {
        return personRepository.findAll(pageable).map(PersonMapper::convertToDtoWithInvites);
    }

    @Override
    public List<PersonShortDTO> getPersonWithNameLike(String likeName) {
        return personRepository.findByNameLike(likeName).stream().map(PersonMapper::convertToShortDto).collect(Collectors.toList());
    }
}
