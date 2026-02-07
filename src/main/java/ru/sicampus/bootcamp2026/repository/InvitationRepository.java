package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.InvitationStatus;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    List<Invitation> findAllByMeetingId(long meetingId);

    List<Invitation> findAllByUserIdAndStatus(long userId, InvitationStatus status);

}
