package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private long id;
    private String username;
    private String surname;
    private String name;
    private String patronymic;
    private String departmentName;
    private String photoUrl;
    private String messengerLink;
    private String phoneNumber;
    private String Email;
    private List<Long> invitedMeetingIds;      //Ids of meetings to which the user is invited
}
