package ru.sicampus.bootcamp2026.util.checkers;

import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.exception.MeetingNotFoundException;
import ru.sicampus.bootcamp2026.repository.MeetingRepository;

public class MeetingChecker {
    public static Meeting checkMeeting(MeetingRepository meetingRepository, Long id) {
        return meetingRepository
                .findById(id)
                .orElseThrow(MeetingNotFoundException::new);
    }
}
