package ru.sicampus.bootcamp2026.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.sicampus.bootcamp2026.domain.Meeting;
import ru.sicampus.bootcamp2026.domain.User;
import ru.sicampus.bootcamp2026.domain.UserMeeting;
import ru.sicampus.bootcamp2026.domain.UserMeetingId;

import java.time.OffsetDateTime;
import java.util.List;

public interface UserMeetingRepository extends JpaRepository<UserMeeting, UserMeetingId> {

    void deleteByMeeting(Meeting meeting);

    @Query("select um.meeting from UserMeeting um where um.user.id = :userId and um.status = :status")
    List<Meeting> findMeetingsByUserIdAndStatus(@Param("userId") long userId, @Param("status") String status);

    void deleteByUser(User user);

    @Query("""
        SELECT COUNT(um) > 0 
        FROM UserMeeting um 
        JOIN um.meeting m 
        WHERE um.user.id = :userId 
        AND um.status = 'ACCEPTED' 
        AND (m.startsAt < :end AND m.endsAt > :start)
    """)
    boolean existsByUserIdAndTimeRange(
            @Param("userId") Long userId,
            @Param("start") OffsetDateTime start,
            @Param("end") OffsetDateTime end
    );
}