package ru.sicampus.bootcamp2026.exception;

public class InvitationException extends BaseException {
    public static InvitationException notFound() {
        return new InvitationException(ErrorCode.INVITATION_NOT_FOUND, "invitation not found");
    }

    public static InvitationException accessDenied() {
        return new InvitationException(ErrorCode.INVITATION_ACCESS_DENIED, "you cant access this invitation");
    }

    public static InvitationException exists() {
        return new InvitationException(ErrorCode.INVITATION_ALREADY_EXISTS, "invitation for this user and meeting already exists");
    }

    public static InvitationException alreadyResponded() {
        return new InvitationException(ErrorCode.INVITATION_ALREADY_RESPONDED, "you have already responded to this invitation");
    }

    protected InvitationException(ErrorCode code, String message) {
        super(code, message);
    }
}
