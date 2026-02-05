package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.UserDTO;
import ru.sicampus.bootcamp2026.entity.User;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class UserMapper {
    public UserDTO convertToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setId(user.getId());
        userDTO.setSurname(user.getSurname());
        userDTO.setName(user.getName());
        userDTO.setPatronymic(user.getPatronymic());
        userDTO.setDepartmentName(user.getDepartmentName());
        userDTO.setPhotoUrl(user.getPhotoUrl());
        userDTO.setMessengerLink(user.getMessengerLink());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setEmail(user.getEmail());

        List<Long> invitedMeetingIds = new ArrayList<>();
        user.getInvites().forEach(invite -> {
            if (!invite.isAccepted()) {
                invitedMeetingIds.add(invite.getMeeting().getId());
            }
        });
        userDTO.setInvitedMeetingIds(invitedMeetingIds);

        return userDTO;
    }
}
