package ru.example.edu.dto;

import lombok.Data;

import java.util.List;

@Data
public class PersonDTO {
    private long id;
    private String name;
    private String login;
    private String photoUrl;
    private String departmentName;
    private List<MeetupWithInvitesDTO> meetups;
}
