package ru.examle.edu.service.impl;

import lombok.RequiredArgsConstructor;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final DepartmentRepository departmentRepository;


    @Override
    public List<PersonDTO> getAllPersons() {
//        List<Person> all = personRepository.findAll();
//        List<PersonDTO> res = new ArrayList<>();
//        for (Person person : all) {
//            res.add(PersonMapper.convertToDTO(person));
//        }
//        return res;
        return personRepository.findAll().stream()
                .map(PersonMapper::convertToDTO)
                .collect(Collectors.toList());
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
