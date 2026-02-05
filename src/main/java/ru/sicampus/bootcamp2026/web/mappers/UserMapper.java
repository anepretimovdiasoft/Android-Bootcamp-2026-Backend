package ru.sicampus.bootcamp2026.web.mappers;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.entity.User;
import ru.sicampus.bootcamp2026.web.dto.user.UserDto;
import ru.sicampus.bootcamp2026.web.dto.user.UserMiniDto;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .description(user.getDescription())
                .position(user.getPosition())
                .department(user.getDepartment())
                .photoUrl(user.getPhotoUrl())
                .role(user.getRole().getName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public UserMiniDto toMiniDto(User user) {
        return UserMiniDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .photoUrl(user.getPhotoUrl())
                .build();
    }

    public List<UserMiniDto> toMiniDtoList(List<User> users) {
        return users.stream()
                .map(UserMapper::toMiniDto)
                .collect(Collectors.toList());
    }

}
