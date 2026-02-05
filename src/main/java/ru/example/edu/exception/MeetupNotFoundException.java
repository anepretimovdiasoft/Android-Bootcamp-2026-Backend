package ru.example.edu.exception;

public class MeetupNotFoundException extends RuntimeException {
    public MeetupNotFoundException(String message) {
        super(message);
    }
}
