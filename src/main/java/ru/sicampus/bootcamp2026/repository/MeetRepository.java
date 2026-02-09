package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Meet;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MeetRepository extends JpaRepository<Meet, Long> {
    Page<Meet> findByOrganizerId(Long organizerId, Pageable pageable);

    @Query("SELECT m FROM Meet m WHERE m.meetDate = :date ORDER BY m.meetTime")
    Page<Meet> findByMeetDate(@Param("date") LocalDate date, Pageable pageable);


    @Query("SELECT m FROM Meet m WHERE m.meetDate BETWEEN :startDate AND :endDate " +
            "ORDER BY m.meetDate, m.meetTime")
    Page<Meet> findByMeetDateBetween(@Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate,
                                     Pageable pageable);

    @Query("SELECT m FROM Meet m WHERE " +
            "(m.organizer.id = :userId OR EXISTS " +
            "(SELECT i FROM Invitation i WHERE i.meet.id = m.id AND i.user.id = :userId AND i.status = 'ACCEPTED')) " +
            "AND m.meetDate = :date AND m.meetTime = :time")
    List<Meet> findConflicts(@Param("userId") Long userId,
                             @Param("date") LocalDate date,
                             @Param("time") java.time.LocalTime time);
}