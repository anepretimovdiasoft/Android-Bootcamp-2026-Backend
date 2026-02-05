package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entity.MeetingParticipant;

import java.util.List;
import java.util.Optional;

public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipant, Long> {
    List<MeetingParticipant> findByMeetingId(Long meetingId);
    List<MeetingParticipant> findByUserId(Long userId);
    Optional<MeetingParticipant> findByMeetingIdAndUserId(Long meetingId, Long userId);
    boolean existsByMeetingIdAndUserId(Long meetingId, Long userId);
}