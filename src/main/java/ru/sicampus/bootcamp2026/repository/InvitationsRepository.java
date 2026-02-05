package ru.sicampus.bootcamp2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.entity.Invitations;
import ru.sicampus.bootcamp2026.entity.Users;

import java.util.List;

@Repository
public interface InvitationsRepository extends JpaRepository<Invitations, Long> {
    List<Invitations> findAllByInvitedUserId(Users user);
}