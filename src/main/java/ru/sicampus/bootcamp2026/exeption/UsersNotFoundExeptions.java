package ru.sicampus.bootcamp2026.exeption;

public class UsersNotFoundExeptions extends RuntimeException {
    public UsersNotFoundExeptions(String message) {
        super(message);
    }
}
