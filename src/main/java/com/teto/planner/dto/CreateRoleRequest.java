package com.teto.planner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateRoleRequest(
        @NotBlank @Size(max = 32) String slug,
        @NotBlank @Size(max = 64) String name
) {
}
