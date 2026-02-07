package ru.sicampus.bootcamp2026.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Employee_User")
    private Employee employee;
    private  String name;
    private  String contact;
    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }
}
