package ru.example.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.edu.entity.Invite;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {
}
