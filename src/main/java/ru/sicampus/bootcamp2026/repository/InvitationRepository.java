package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Invitation;
import ru.sicampus.bootcamp2026.entity.InvitationStatus;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findAllByTarget(Users target);

    @Query("SELECT i FROM Invitation i " +
            "WHERE i.target = :target " +
            "AND i.meeting = :meeting " +
            "AND i.status = :status")
    Optional<Invitation> findByTargetAndMeetingWithStatuses(
            @Param("target") Users target,
            @Param("meeting") Meeting meeting,
            @Param("status") InvitationStatus status);

}
