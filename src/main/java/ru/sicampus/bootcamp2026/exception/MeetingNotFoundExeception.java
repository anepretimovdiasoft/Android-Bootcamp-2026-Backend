package ru.sicampus.bootcamp2026.exception;

public class MeetingNotFoundExeception extends RuntimeException {
    public MeetingNotFoundExeception(String message) {
        super(message);
    }
}
