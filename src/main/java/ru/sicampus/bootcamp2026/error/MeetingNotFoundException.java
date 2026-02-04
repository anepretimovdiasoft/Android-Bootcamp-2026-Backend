package ru.sicampus.bootcamp2026.error;

public class MeetingNotFoundException extends RuntimeException {
  public MeetingNotFoundException(String message) {
    super(message);
  }
}
