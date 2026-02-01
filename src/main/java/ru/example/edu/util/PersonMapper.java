package ru.example.edu.util;

import lombok.experimental.UtilityClass;
import ru.example.edu.dto.PersonDTO;
import ru.example.edu.dto.PersonShortDTO;
import ru.example.edu.dto.PersonWithInvitesShortDTO;
import ru.example.edu.dto.PersonWithInvitesDTO;
import ru.example.edu.entity.Person;

import java.util.stream.Collectors;

@UtilityClass
public class PersonMapper {
    public PersonWithInvitesDTO convertToDtoWithInvites(Person person) {
        PersonWithInvitesDTO personDTO = new PersonWithInvitesDTO();
        personDTO.setId(person.getId());
        personDTO.setLogin(person.getLogin());
        personDTO.setName(person.getName());
        personDTO.setPhotoUrl(person.getPhotoUrl());
        personDTO.setDepartmentName(person.getDepartment().getName());
        personDTO.setMeetups(person.getMeetups().stream().map(MeetupMapper::convertToShortDto).collect(Collectors.toList()));
        personDTO.setInvites(person.getInvites().stream().map(InviteMapper::convertToDtoWithMeetup).collect(Collectors.toList()));
        return personDTO;
    }

    public PersonWithInvitesShortDTO convertToShortDtoWithInvites(Person person) {
        PersonWithInvitesShortDTO personDTO = new PersonWithInvitesShortDTO();
        personDTO.setId(person.getId());
        personDTO.setLogin(person.getLogin());
        personDTO.setName(person.getName());
        personDTO.setPhotoUrl(person.getPhotoUrl());
        personDTO.setDepartmentName(person.getDepartment().getName());
        return personDTO;
    }

    public PersonShortDTO convertToShortDto(Person person) {
        PersonShortDTO personDTO = new PersonShortDTO();
        personDTO.setId(person.getId());
        personDTO.setLogin(person.getLogin());
        personDTO.setName(person.getName());
        personDTO.setPhotoUrl(person.getPhotoUrl());
        personDTO.setDepartmentName(person.getDepartment().getName());
        return personDTO;
    }

    public PersonDTO convertToDto(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setLogin(person.getLogin());
        personDTO.setName(person.getName());
        personDTO.setPhotoUrl(person.getPhotoUrl());
        personDTO.setDepartmentName(person.getDepartment().getName());
        personDTO.setMeetups(person.getMeetups().stream().map(MeetupMapper::convertToShortDtoWithInvites).collect(Collectors.toList()));
        return personDTO;
    }

}
