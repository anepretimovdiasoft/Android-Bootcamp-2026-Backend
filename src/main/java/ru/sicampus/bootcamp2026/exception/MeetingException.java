package ru.sicampus.bootcamp2026.exception;

import ru.sicampus.bootcamp2026.dto.response.MeetingResponseDTO;

import java.util.List;
import java.util.Map;

public class MeetingException extends BaseException {
    public static MeetingException notFound() {
        return new MeetingException(ErrorCode.MEETING_NOT_FOUND, "meeting not found");
    }

    public static MeetingException timeConflict(List<MeetingResponseDTO> conflicts) {
        return new MeetingException(
                ErrorCode.MEETING_TIME_CONFLICT,
                "this meeting conflicts with others",
                Map.of("conflicts", conflicts)
        );
    }

    public static MeetingException accessDenied() {
        return new MeetingException(ErrorCode.MEETING_ACCESS_DENIED, "you have no access to this meeting");
    }

    protected MeetingException(ErrorCode code, String message) {
        super(code, message);
    }

    protected MeetingException(ErrorCode code, String message, Map<String, Object> details) {
        super(code, message, details);
    }
}
