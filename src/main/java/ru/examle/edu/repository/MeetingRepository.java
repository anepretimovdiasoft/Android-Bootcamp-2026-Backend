package ru.examle.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.examle.edu.entity.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
