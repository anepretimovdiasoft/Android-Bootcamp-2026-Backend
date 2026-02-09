package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authorities")
@Getter
@Setter
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String authority;

    @Override
    public String getAuthority() {
        return this.authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authority)) return false;
        Authority that = (Authority) o;
        return authority != null && authority.equals(that.authority);
    }

    @Override
    public int hashCode() {
        return authority != null ? authority.hashCode() : 0;
    }

    @Override
    public String toString() {
        return authority;
    }
}