package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Invitation;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Page<Invitation> findByUserId(Long userId, Pageable pageable);
    Page<Invitation> findByUserIdAndStatus(Long userId, String status, Pageable pageable);
    List<Invitation> findByMeetId(Long meetId);
    Optional<Invitation> findByMeetIdAndUserId(Long meetId, Long userId);
    boolean existsByMeetIdAndUserId(Long meetId, Long userId);

    @Query("SELECT i FROM Invitation i WHERE i.user.id = :userId AND i.status = 'PENDING'")
    Page<Invitation> findPendingByUserId(@Param("userId") Long userId, Pageable pageable);
}