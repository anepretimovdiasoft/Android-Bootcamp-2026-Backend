package ru.sicampus.bootcamp2026.dto.request;

import jakarta.validation.constraints.NotNull;
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
    private List<UUID> userId;
}
