package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.UserDTO;
import ru.sicampus.bootcamp2026.entity.User;

@UtilityClass
public class UserMapper {
    public UserDTO convertToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPasswordHash(user.getPasswordHash());
        userDTO.setFullName(user.getFullName());
        userDTO.setPfpUrl(user.getPfpUrl());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }
}
