package ru.example.edu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class PersonShortDTO extends PersonDTO {
    @JsonIgnore
    private List<MeetupWithInvitesDTO> meetups;
}
