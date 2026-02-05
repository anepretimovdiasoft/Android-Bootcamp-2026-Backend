package ru.sicampus.bootcamp2026.dto;

import lombok.Data;
import ru.sicampus.bootcamp2026.entity.Meeting;

@Data
public class InvitationDTO {
    private long id;
    private long userId;        //Invited user's id
    private long meetingId;     //Id of the meeting in the invitation
    private boolean isAccepted;
}
