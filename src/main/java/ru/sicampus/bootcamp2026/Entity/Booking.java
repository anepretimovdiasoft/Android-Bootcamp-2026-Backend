package ru.sicampus.bootcamp2026.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(name="start_Booking")
    private LocalDateTime start;
    @Column (name = "end_Booking")
    private LocalDateTime end;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Employee_Admin")
    private Employee employee;
    @OneToMany(mappedBy = "booking",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private ArrayList<Invitations> invitations=new ArrayList<>();

    public LocalDateTime getStart() {
        return start;
    }

    public Object getEmployee() {
        return employee;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}
