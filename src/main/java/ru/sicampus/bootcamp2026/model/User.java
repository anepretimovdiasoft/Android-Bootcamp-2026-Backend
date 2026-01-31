package ru.sicampus.bootcamp2026.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)//переопределяет e&hc только для полей с аннотацией @EqualsAndHashCode.Include
@Table(name = "users")/*
       TODO: добавить индексы
*/
public class User {

    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @GeneratedValue
    @EqualsAndHashCode.Include
    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @NotBlank//валидация на уровне приложения
    @Size(max = 50)
    @Column(name = "username", nullable = false, length = 50)//валидация на уровне БД
    private String username;

    @NotBlank
    @Email
    @Size(max = 255)//стандарт RFC 5321 максимум 254 символа
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(min = 60, max = 255)//минимум для BCrypt хэша
    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    @Size(max = 2048)
    @Column(name = "avatar_url")
    private String avatar_url;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<MeetingParticipant> meetingParticipants = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,//удаление всех токенов вместе с пользователем
            orphanRemoval = true,//удаление токена из коллекции -> удаление из бд
            fetch = FetchType.LAZY
    )
    private List<RefreshToken> refreshTokens = new ArrayList<>();
}
