package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.UserDto;
import ru.sicampus.bootcamp2026.entity.User;

@UtilityClass
public class UserMapper {

    public UserDto toDto(User entity) {
        var dto = new UserDto();

        dto.setId(entity.getId());
        dto.setLogin(dto.getLogin());
        dto.setName(entity.getName());
        dto.setLastname(entity.getLastname());
        dto.setAboutMe(entity.getAboutMe());
        dto.setPhotoUrl(entity.getPhotoUrl());
        dto.setPosition(entity.getPosition().getPosition());

        return dto;
    }
}
