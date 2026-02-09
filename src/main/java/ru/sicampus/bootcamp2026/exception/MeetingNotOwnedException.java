package ru.sicampus.bootcamp2026.exception;

public class MeetingNotOwnedException extends RuntimeException {
    public MeetingNotOwnedException(String message) {
        super(message);
    }
}
