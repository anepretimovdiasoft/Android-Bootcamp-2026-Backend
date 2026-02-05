package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.invitations;
import java.util.List;
import java.util.Optional;

@Repository
public interface invitationsRepository extends JpaRepository<invitations, Long> {
    List<invitations> findByUserId(Long userId);
    List<invitations> findByMeetId(Long meetId);
    Optional<invitations> findByMeetIdAndUserId(Long meetId, Long userId);
    boolean existsByMeetIdAndUserId(Long meetId, Long userId);
    long countByMeetId(Long meetId);
}