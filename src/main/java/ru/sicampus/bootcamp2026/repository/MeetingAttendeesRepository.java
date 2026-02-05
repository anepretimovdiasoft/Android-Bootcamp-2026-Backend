package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.model.entity.MeetingAttendees;
import ru.sicampus.bootcamp2026.model.entity.MeetingAttendeesId;
import ru.sicampus.bootcamp2026.model.entity.Users;

import java.util.List;


@Repository
public interface MeetingAttendeesRepository extends JpaRepository<
        ru.sicampus.bootcamp2026.model.entity.MeetingAttendees, MeetingAttendeesId> {
    List<MeetingAttendees> findAllByUser(Users user);

}
