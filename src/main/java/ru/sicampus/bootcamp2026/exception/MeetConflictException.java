package ru.sicampus.bootcamp2026.exception;

public class MeetConflictException extends RuntimeException {
    public MeetConflictException(String message) {
        super(message);
    }
}