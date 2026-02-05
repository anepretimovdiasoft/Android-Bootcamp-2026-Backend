package ru.sicampus.bootcamp2026.utils;

import ru.sicampus.bootcamp2026.dtos.UserDto;
import ru.sicampus.bootcamp2026.entities.User;

public class UserMapper {
    public static UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPhotoUrl(user.getPhotoUrl());
        userDto.setPosition(user.getPosition());
        return userDto;
    }
}
