package ru.sicampus.bootcamp2026.exception;

public class PersonAuthorityNotFoundException extends RuntimeException {
    public PersonAuthorityNotFoundException(String message) {
        super(message);
    }
    public PersonAuthorityNotFoundException() {
        super("Authority not found!");
    }
}
