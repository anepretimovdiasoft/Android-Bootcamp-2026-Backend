package ru.sicampus.bootcamp2026.exception.user;

public class UserAlreadyExist extends RuntimeException {
    public UserAlreadyExist(String message) {
        super(message);
    }
}
