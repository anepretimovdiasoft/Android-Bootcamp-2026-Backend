package ru.examle.edu.repository;

import ru.examle.edu.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findByPersonId(Long personId);
    List<Invitation> findByMeetingId(Long meetingId);
    Optional<Invitation> findByMeetingIdAndPersonId(Long meetingId, Long personId);
}