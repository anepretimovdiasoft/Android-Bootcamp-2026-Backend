package ru.sicampus.bootcamp2026.error;

public class WrongInvitationException extends RuntimeException {
  public WrongInvitationException(String message) {
    super(message);
  }
}
