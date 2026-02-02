package ru.sicampus.bootcamp2026.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sicampus.bootcamp2026.entity.InvitationStatus;
import java.util.Optional;

public interface InvitationStatusRepository extends JpaRepository<InvitationStatus, Long> {
    Optional<InvitationStatus> findByStatusName(String statusName);
}