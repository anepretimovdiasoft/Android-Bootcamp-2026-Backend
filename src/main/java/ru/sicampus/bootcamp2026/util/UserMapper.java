package ru.sicampus.bootcamp2026.util;

import ru.sicampus.bootcamp2026.domain.User;
import ru.sicampus.bootcamp2026.dto.UserDtos.UserResponse;

public final class UserMapper {

    private UserMapper() {}

    public static UserResponse toResponse(User u) {
        return new UserResponse(u.getId(), u.getRole(), u.getName(), u.getLogin());
    }
}