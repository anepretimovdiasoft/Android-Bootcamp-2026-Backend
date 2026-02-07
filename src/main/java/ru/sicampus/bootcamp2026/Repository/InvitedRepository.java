package ru.sicampus.bootcamp2026.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.Entity.Employee;
import ru.sicampus.bootcamp2026.Entity.Invitations;
import ru.sicampus.bootcamp2026.Entity.Invited;

import java.util.List;

@Repository
public interface InvitedRepository extends JpaRepository<Invited,Long> {
    List<Invited> findByInvitations(Invitations invitations);

    List<Invited> findByEmployee(Employee employee);
}
