package ru.sicampus.bootcamp2026.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import ru.sicampus.bootcamp2026.domain.Meeting;
import ru.sicampus.bootcamp2026.domain.User;
import ru.sicampus.bootcamp2026.domain.UserMeeting;
import ru.sicampus.bootcamp2026.domain.UserMeetingId;

public interface UserMeetingRepository extends JpaRepository<UserMeeting, UserMeetingId> {
    void deleteByMeeting(Meeting meeting);

    @Query("select um.meeting from UserMeeting um where um.user.id = :userId and um.accepted = :status")
    List<Meeting> findMeetingsByUserIdAndStatus(@Param("userId") long userId, @Param("status") String status);

    void deleteByUser(User user);
}