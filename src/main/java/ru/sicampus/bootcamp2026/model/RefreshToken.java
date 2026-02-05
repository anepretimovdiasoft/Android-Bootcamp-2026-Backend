package ru.sicampus.bootcamp2026.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "refresh_tokens") /*
        TODO: добавить индексы
*/
public class RefreshToken {

    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @GeneratedValue
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)// У одного пользователя может быть множество токенов (пк, телефон, ноутбук)
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @Column(name = "token", nullable = false, length = 2048)
    private String token;

    @Column(name= "token_version", nullable = false)
    private long tokenVersion;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "revoked", nullable = false)
    private boolean revoked = false;

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    @PrePersist // Автоматическое выполнение до insert в БД
    public void onCreate(){
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

}

