package ru.examle.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.examle.edu.dto.PersonDTO;
import ru.examle.edu.enity.Department;
import ru.examle.edu.enity.Person;
import ru.examle.edu.exception.DepartmentNotFoundException;
import ru.examle.edu.exception.PersonNotFoundException;
import ru.examle.edu.repository.DepartmentRepository;
import ru.examle.edu.repository.PersonRepository;
import ru.examle.edu.service.PersonService;
import ru.examle.edu.ulti.PersonMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final DepartmentRepository departmentRepository;


    @Override
    public Page<PersonDTO> getAllPersons(Pageable pageable) {
        return personRepository.findAll(pageable).map(PersonMapper::convertToDTO);
    }

    @Override
    public PersonDTO getPersonById(Long id) {
        return personRepository.findById(id).map(PersonMapper::convertToDTO)
                .orElseThrow(() -> new PersonNotFoundException("Person not found!!!"));
    }

    @Override
    public PersonDTO createPerson(PersonDTO dto) {
        Optional<Department> optionalDepartment = departmentRepository.findByName(dto.getDepartmentName());
        if (optionalDepartment.isEmpty()) {
            throw new DepartmentNotFoundException("Department not found!!!");
        }


        Person person = new Person();
        person.setName(dto.getName());
        person.setEmail(dto.getEmail());
        person.setPhotoUrl(dto.getPhotoUrl());
        person.setDepartment(optionalDepartment.get());


        return PersonMapper.convertToDTO(personRepository.save(person));
    }

    @Override
    public PersonDTO updatePerson(Long id, PersonDTO dto) {
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found!"));

        person.setName(dto.getName());
        person.setEmail(dto.getEmail());
        person.setPhotoUrl(dto.getPhotoUrl());

        Optional<Department> department = departmentRepository.findByName(dto.getDepartmentName());
        department.ifPresent(person::setDepartment);

        return PersonMapper.convertToDTO(personRepository.save(person));
    }

    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}
