package ru.sicampus.bootcamp2026.Dto.requst.Infitations;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmployeeNamesRequest {
    @NotNull
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }
}
