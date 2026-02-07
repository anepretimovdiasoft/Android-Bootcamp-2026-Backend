package ru.sicampus.bootcamp2026.exception;

public class PersonAlreadyExistsException extends RuntimeException {
    public PersonAlreadyExistsException(String message) {
        super(message);
    }
    public PersonAlreadyExistsException() {
        super("Person already exists!");
    }
}
