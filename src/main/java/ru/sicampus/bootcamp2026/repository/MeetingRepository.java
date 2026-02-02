package ru.sicampus.bootcamp2026.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sicampus.bootcamp2026.entity.Meeting;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query("SELECT m FROM Meeting m JOIN MeetingParticipant mp ON m.id = mp.meeting.id " +
            "WHERE mp.user.id = :userId " +
            "AND mp.invitationStatus.statusName = 'ACCEPTED' " +
            "AND m.calendarDate BETWEEN :startDate AND :endDate")
    List<Meeting> findConfirmedMeetings(Long userId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT m FROM Meeting m JOIN MeetingParticipant mp ON m.id = mp.meeting.id " +
            "WHERE mp.user.id = :userId " +
            "AND mp.invitationStatus.statusName = 'ACCEPTED' " +
            "AND m.calendarDate = :date " +
            "AND (m.startTime < :endTime AND m.endTime > :startTime)")
    List<Meeting> findConflictingMeetings(Long userId, LocalDate date, LocalTime startTime, LocalTime endTime);
}