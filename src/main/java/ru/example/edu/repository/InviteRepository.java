package ru.example.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.edu.entity.Invite;

import java.util.List;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {
    List<Invite> findByParticipantId(Long id);
}
