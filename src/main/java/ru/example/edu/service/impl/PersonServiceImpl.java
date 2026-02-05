package ru.example.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.edu.dto.PersonWithInvitesDTO;
import ru.example.edu.entity.Department;
import ru.example.edu.entity.Person;
import ru.example.edu.exception.DepartmentNotFoundException;
import ru.example.edu.exception.PersonNotFoundException;
import ru.example.edu.repository.DepartmentRepository;
import ru.example.edu.repository.PersonRepository;
import ru.example.edu.service.PersonService;
import ru.example.edu.util.PersonMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public List<PersonWithInvitesDTO> getAllPersons() {
        return personRepository.findAll().stream().map(PersonMapper::convertToDtoWithInvites).collect(Collectors.toList());
    }

    @Override
    public PersonWithInvitesDTO getPersonByUd(Long id) {
        return personRepository.findById(id).map(PersonMapper::convertToDtoWithInvites).orElseThrow(() -> new PersonNotFoundException("Person not found!"));
    }

    @Override
    public PersonWithInvitesDTO createPerson(PersonWithInvitesDTO dto) {
        Optional<Department> optionalDepartment = departmentRepository.findByName(dto.getDepartmentName());
        if (optionalDepartment.isEmpty()) {
            throw new DepartmentNotFoundException("Department not found!");
        }
        Person person = new Person();
        person.setName(dto.getName());
        person.setPhotoUrl(dto.getPhotoUrl());
        person.setLogin(dto.getLogin());
        person.setDepartment(optionalDepartment.get());
        return PersonMapper.convertToDtoWithInvites(personRepository.save(person));
    }

    @Override
    public PersonWithInvitesDTO updatePerson(Long id, PersonWithInvitesDTO dto) {
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found!"));

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
}
