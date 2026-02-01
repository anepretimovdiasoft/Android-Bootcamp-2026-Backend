package ru.example.edu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class PersonWithInvitesShortDTO extends PersonWithInvitesDTO {
    @JsonIgnore
    private List<InviteWithMeetupDTO> invites;

    @JsonIgnore
    private List<MeetupDTO> meetups;
}
