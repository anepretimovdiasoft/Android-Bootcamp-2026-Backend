package ru.example.edu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MeetupShortDTO extends MeetupDTO {
    @JsonIgnore
    private PersonWithInvitesShortDTO planner;
}
