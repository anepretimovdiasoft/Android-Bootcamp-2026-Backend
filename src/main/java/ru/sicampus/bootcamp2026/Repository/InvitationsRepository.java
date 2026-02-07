package ru.sicampus.bootcamp2026.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sicampus.bootcamp2026.Entity.Booking;
import ru.sicampus.bootcamp2026.Entity.Invitations;

@Repository
public interface InvitationsRepository extends JpaRepository<Invitations,Long> {
    Invitations findByBooking(Booking booking);
}