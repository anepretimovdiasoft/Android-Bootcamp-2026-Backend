package ru.sicampus.bootcamp2026.exceptions;

public class MeetingNotExist extends RuntimeException {
    public MeetingNotExist(String message) {
        super(message);
    }
}
