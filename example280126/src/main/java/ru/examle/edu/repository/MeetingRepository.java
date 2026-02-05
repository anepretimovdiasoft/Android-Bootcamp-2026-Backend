package ru.examle.edu.repository;

import ru.examle.edu.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByOrganizerId(Long organizerId);
    List<Meeting> findByMeetingDate(LocalDate meetingDate);

    @Query("SELECT m FROM Meeting m WHERE m.organizer.id = :personId OR EXISTS " +
            "(SELECT i FROM m.invitations i WHERE i.person.id = :personId)")
    List<Meeting> findAllMeetingsForPerson(@Param("personId") Long personId);
}