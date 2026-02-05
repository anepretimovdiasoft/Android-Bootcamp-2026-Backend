package ru.sicampus.bootcamp2026.exception;

import lombok.Getter;

@Getter
public class UserExistsException extends BaseException {
    private final String email;

    public UserExistsException(String email) {
        super(
                ErrorCode.USER_ALREADY_EXISTS,
                String.format("User with email=%s already exists", email)
        );
        this.email = email;
    }
}