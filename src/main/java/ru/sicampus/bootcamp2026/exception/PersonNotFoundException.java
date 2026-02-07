package ru.sicampus.bootcamp2026.exception;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException() {
        super("Person not found!");
    }

    public PersonNotFoundException(String message) {
        super(message);
    }
}
