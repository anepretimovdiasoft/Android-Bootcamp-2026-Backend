package ru.sicampus.bootcamp2026.Dto.requst.Infitations;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class GetInvitationsCreatedRequest {
    @NotBlank
    @NotNull
    private String name;
    @NotEmpty
    @Valid
    private List<EmployeeNamesRequest> invited;

    public String getName() {
        return name;
    }
}
