package ru.sicampus.bootcamp2026.error;

public class InvitationNotFoundException extends RuntimeException {
  public InvitationNotFoundException(String message) {
    super(message);
  }
}
