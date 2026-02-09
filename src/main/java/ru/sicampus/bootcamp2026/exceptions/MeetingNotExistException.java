package ru.sicampus.bootcamp2026.exceptions;

public class MeetingNotExistException extends RuntimeException {
    public MeetingNotExistException(String message) {
        super(message);
    }
}
