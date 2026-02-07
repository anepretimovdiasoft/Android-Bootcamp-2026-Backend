package ru.sicampus.bootcamp2026.Dto.requst.Employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.stream.Collectors;

@Data
@Builder
public class GetEmployeeRequest {
    @NotBlank(message = "df")
    @NotNull(message = "fgh")
    private String name;
    @NotBlank(message = "df")
    @NotNull(message = "fgh")
    private String last_name;
    @NotBlank(message = "df")
    @NotNull(message = "fgh")
    private String father_name;
    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFather_name() {
        return father_name;
    }
}
