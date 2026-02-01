package ru.example.edu.util;

import lombok.experimental.UtilityClass;
import ru.example.edu.dto.InviteDTO;
import ru.example.edu.dto.InviteWithPersonDTO;
import ru.example.edu.dto.InviteWithMeetupDTO;
import ru.example.edu.entity.Invite;

@UtilityClass
public class InviteMapper {
    public InviteWithMeetupDTO convertToDtoWithMeetup(Invite invite) {
        InviteWithMeetupDTO inviteDTO = new InviteWithMeetupDTO();
        inviteDTO.setId(invite.getId());
        inviteDTO.setMeetup(MeetupMapper.convertToDto(invite.getMeetup()));
        inviteDTO.setAgree(invite.getAgree());

        return inviteDTO;
    }

    public InviteWithPersonDTO convertToDtoWithPerson(Invite invite) {
        InviteWithPersonDTO inviteDTO = new InviteWithPersonDTO();
        inviteDTO.setId(invite.getId());
        inviteDTO.setParticipant(PersonMapper.convertToShortDto(invite.getParticipant()));
        inviteDTO.setAgree(invite.getAgree());

        return inviteDTO;
    }

    public InviteDTO convertToDto(Invite invite) {
        InviteDTO inviteDTO = new InviteDTO();
        inviteDTO.setAgree(invite.getAgree());
        inviteDTO.setParticipant(PersonMapper.convertToShortDto(invite.getParticipant()));
        inviteDTO.setMeetup(MeetupMapper.convertToDto(invite.getMeetup()));

        return inviteDTO;
    }
}
