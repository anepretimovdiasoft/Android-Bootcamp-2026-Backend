package ru.sicampus.bootcamp2026.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAvatarRequest {

    @NotBlank(message = "URL аватара обязателен")
    @Size(max = 2048, message = "URL аватара не должен превышать 2048 символов")
    private String avatarUrl;
}
