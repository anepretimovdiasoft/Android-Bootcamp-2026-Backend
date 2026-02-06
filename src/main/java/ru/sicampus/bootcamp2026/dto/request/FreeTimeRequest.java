package ru.sicampus.bootcamp2026.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreeTimeRequest {

    @NotNull(message = "Хотя бы один участник обязателен")
    @Size(min = 1, message = "Должен быть хотя бы один участник")
    private List<UUID> userIds;
}
