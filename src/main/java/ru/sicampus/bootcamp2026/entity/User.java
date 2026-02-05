package ru.sicampus.bootcamp2026.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", nullable = false, unique = true)
    @NotBlank
    private String login;

    @Column(name = "password", nullable = false)
    @NotBlank
    private String password;

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @Column(name = "lastname", nullable = false)
    @NotBlank
    private String lastname;

    @Column(name = "about_me")
    private String aboutMe;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    @NotNull
    private Position position;

    @Column(name = "photo_url")
    private String photoUrl;

    @ToString.Exclude
    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL)
    private List<Meeting> sentInvitations;

    @ToString.Exclude
    @OneToMany(mappedBy = "invitee")
    private List<Invitation> receivedInvitations;

    @ToString.Exclude
    @OneToMany(mappedBy = "organizer")
    private List<Meeting> organizedMeetings;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
