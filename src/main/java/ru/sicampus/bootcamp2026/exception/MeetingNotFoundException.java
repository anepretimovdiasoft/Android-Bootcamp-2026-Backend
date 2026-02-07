package ru.sicampus.bootcamp2026.exception;

public class MeetingNotFoundException extends RuntimeException {
    public MeetingNotFoundException() {
        super("Meeting not found!");
    }
}
