package ru.sicampus.bootcamp2026.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="invited")
public class Invited {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Employee_id")
    private Employee employee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="invitations_id")
    private Invitations invitations;
    private boolean Approval;

    public Employee getEmployee() {
        return employee;
    }

    public Boolean getApproval() {
        return  Approval;
    }

    public Invitations getInvitations() {
        return invitations;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setInvitations(Invitations invitations) {
        this.invitations= this.invitations;
    }
}
