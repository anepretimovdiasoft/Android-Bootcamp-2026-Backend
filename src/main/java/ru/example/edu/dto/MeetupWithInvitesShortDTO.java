package ru.example.edu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MeetupWithInvitesShortDTO extends MeetupWithInvitesDTO {
    @JsonIgnore
    private PersonShortDTO planner;
}
