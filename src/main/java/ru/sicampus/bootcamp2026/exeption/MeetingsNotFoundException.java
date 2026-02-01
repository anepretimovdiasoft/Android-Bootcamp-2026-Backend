package ru.sicampus.bootcamp2026.exeption;

public class MeetingsNotFoundException extends RuntimeException {
    public MeetingsNotFoundException(String message) {
        super(message);
    }
}
