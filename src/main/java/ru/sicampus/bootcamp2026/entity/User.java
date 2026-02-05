package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "login_data_id", nullable = false, unique = true)
    private LoginData loginData;

    @Column(name = "full_name", nullable = false)
    private String fullName;

//    TODO добавить ссылку на фото
//    @Column(name = "photo_url")
//    private String photoUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authorities;

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
        loginData.setUser(this);
    }

    @Override
    public String getPassword() {
        return loginData.getPassword();
    }

    @Override
    public String getUsername() { //TODO username нет, только почта при регистрации?
        return loginData.getEmail();
    }
}
