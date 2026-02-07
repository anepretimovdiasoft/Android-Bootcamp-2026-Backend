package ru.sicampus.bootcamp2026.exeptions;

public class MeetingNotFound extends RuntimeException {
    public MeetingNotFound(String message) {
        super(message);
    }
}
