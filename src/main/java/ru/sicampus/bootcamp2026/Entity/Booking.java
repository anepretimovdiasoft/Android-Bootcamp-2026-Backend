package ru.sicampus.bootcamp2026.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "start_booking")
    private LocalDateTime start;

    @Column(name = "end_booking")
    private LocalDateTime end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_admin")
    private Employee employee;

    @OneToMany(
            mappedBy = "booking",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Invitations> invitations = new ArrayList<>();

    public void addInvitation(Invitations invitation) {
        invitations.add(invitation);
        invitation.setBooking(this);
    }

    public void removeInvitation(Invitations invitation) {
        invitations.remove(invitation);
        invitation.setBooking(null);
    }

    public Object getName() {
        return name;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}
   
