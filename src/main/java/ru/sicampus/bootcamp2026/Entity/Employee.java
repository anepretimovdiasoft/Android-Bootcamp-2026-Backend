package ru.sicampus.bootcamp2026.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.support.BeanDefinitionDsl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
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
    @ManyToOne
    @JoinColumn(name="Avatar_id")
    private Avatar avatar;
    private String mail;
    private String password;
    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Booking> bookings=new ArrayList<>();
    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Invitations> invitations=new ArrayList<>();
    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Contact> contacts=new ArrayList<>();
    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Invited> inviteds=new ArrayList<>();
    public Employee(String name, String lastName, String fatherName, String mail, Avatar avatar, int age,String password) {
    }
    
    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFather_name() {
        return father_name;
    }

    public String getMail() {
        return mail;
    }

    public Long getId() {
        return id;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public String getPassword() {
        return password;
    }
}
