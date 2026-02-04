package ru.sicampus.bootcamp2026.exeption;

public class PersonAlreadyExistException extends RuntimeException {
    public PersonAlreadyExistException(String message) {
        super(message);
    }
}
