package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.UserDto;
import ru.sicampus.bootcamp2026.model.entity.Users;



@UtilityClass
public class UserMapper {
    public UserDto toDto(Users user) {
        if (user == null) {
            return null;
        }

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPosition(user.getPosition());
        return dto;
    }
}
