package ru.sicampus.bootcamp2026.web.mappers;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.web.dto.invitation.InvitationDto;
import ru.sicampus.bootcamp2026.web.dto.invitation.UserMiniInvitationDto;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class InvitationMapper {

    public InvitationDto toDto(Invitation invitation) {
        User user = invitation.getUser();
        Meeting meeting = invitation.getMeeting();

        return InvitationDto.builder()
                .id(invitation.getId())
                .authorId(user.getId())
                .authorFirstName(user.getFirstName())
                .authorSecondName(user.getSecondName())
                .meetingId(meeting.getId())
                .title(meeting.getTitle())
                .address(meeting.getAddress())
                .date(meeting.getDate())
                .timeStart(meeting.getTimeStart())
                .timeEnd(meeting.getTimeEnd())
                .build();
    }

    public List<InvitationDto> toDtoList(List<Invitation> invitations) {
        return invitations.stream()
                .map(InvitationMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserMiniInvitationDto toMiniInvitationDto(Invitation invitation) {
        User user = invitation.getUser();

        return UserMiniInvitationDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .photoUrl(user.getPhotoUrl())
                .status(invitation.getStatus().toString())
                .respondedAt(invitation.getRespondedAt())
                .build();
    }

    public List<UserMiniInvitationDto> toMiniInvitationDtoList(List<Invitation> invitations) {
        return invitations.stream()
                .map(InvitationMapper::toMiniInvitationDto)
                .collect(Collectors.toList());
    }

}
