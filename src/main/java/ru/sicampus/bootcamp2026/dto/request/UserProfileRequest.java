package ru.sicampus.bootcamp2026.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequest {

    @Size(max = 50, message = "Имя пользователя не должно превышать 50 символов")
    private String username;

    @Size(max = 2048, message = "URL аватара не должен превышать 2048 символов")
    private String avatarUrl;
}
