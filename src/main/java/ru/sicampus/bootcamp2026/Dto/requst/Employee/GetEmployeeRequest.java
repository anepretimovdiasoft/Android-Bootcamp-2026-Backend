package ru.sicampus.bootcamp2026.Dto.requst.Employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetEmployeeRequest {
    @NotBlank(message = "df")
    @NotNull(message = "fgh")
    private String name;
    @NotBlank

    public String getName() {
        return name;
    }
}
