package ru.sicampus.bootcamp2026.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Avatar")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private  String name;
    @OneToMany(mappedBy = "avatar", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Employee> employee=new ArrayList<>();

    public Avatar(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }
}
