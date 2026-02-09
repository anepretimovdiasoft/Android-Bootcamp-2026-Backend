package ru.sicampus.bootcamp2026.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.Entity.Booking;
import ru.sicampus.bootcamp2026.Entity.Employee;
import ru.sicampus.bootcamp2026.Entity.Invitations;

import java.util.List;

@Repository
public interface InvitationsRepository extends JpaRepository<Invitations,Long> {
    Invitations findByBooking(Booking booking);
    boolean existsByBooking(Booking booking);
    List<Invitations> findByEmployee(Employee enployee);
}