package ru.sicampus.bootcamp2026.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name="Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private  String last_name;
    private String father_name;
    private Boolean сhosenness;
    private long age;
    private String avatar;
    private String mail;
    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private ArrayList<Booking> bookings=new ArrayList<>();
    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private ArrayList<Invitations> invitations=new ArrayList<>();
    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private ArrayList<Contact> contacts=new ArrayList<>();
    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private ArrayList<Invited> inviteds=new ArrayList<>();

    public void setName(String name) {
        this.name=name;
    }
    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public void setFather_name(String father_name){
        this.father_name=father_name;
    }
}
