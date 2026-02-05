package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entity.InvitedParticipant;

import java.util.List;
import java.util.Optional;

public interface InvitedParticipantRepository extends JpaRepository<InvitedParticipant, Long> {
    Optional<List<InvitedParticipant>> findByMeetingId(Long meetingId);
}
