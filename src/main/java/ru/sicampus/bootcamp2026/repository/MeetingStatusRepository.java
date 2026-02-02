package ru.sicampus.bootcamp2026.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entity.MeetingStatus;
import java.util.Optional;

public interface MeetingStatusRepository extends JpaRepository<MeetingStatus, Long> {
    Optional<MeetingStatus> findByStatusName(String statusName);
}