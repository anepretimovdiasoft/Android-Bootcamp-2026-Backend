package ru.sicampus.bootcamp2026.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class UserNotFoundException extends BaseException {
    private final Long userId;
    private final String email;

    public UserNotFoundException(Long userId) {
        super(
                ErrorCode.USER_NOT_FOUND,
                String.format("User with id=%d not found", userId),
                Map.of("userId", userId)
        );
        this.userId = userId;
        this.email = null;
    }

    public UserNotFoundException(String email) {
        super(
                ErrorCode.USER_NOT_FOUND,
                String.format("User with email=%s not found", email),
                Map.of("email", email)
        );
        this.userId = null;
        this.email = email;
    }
}