package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Meeting;
import ru.sicampus.bootcamp2026.entity.Users;
import ru.sicampus.bootcamp2026.entity.UsersMeeting;

import java.util.Optional;

@Repository
public interface UsersMeetingRepository extends JpaRepository<UsersMeeting, Long> {
    Optional<UsersMeeting> findByMemberAndMeeting(Users member, Meeting meeting);
}
