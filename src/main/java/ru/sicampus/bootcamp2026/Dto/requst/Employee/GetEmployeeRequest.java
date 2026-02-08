package ru.sicampus.bootcamp2026.Dto.requst.Employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.stream.Collectors;

@Data
@Builder
public class GetEmployeeRequest {
    @NotBlank(message = "the name is either omitted or an empty string.")
    @NotNull(message = "The name cannot be empty.")
    private String name;
    @NotBlank(message = "the last name cannot be an empty string.")
    @NotNull(message = "The last name cannot be empty.")
    private String last_name;
    @NotBlank(message = "a patronymic cannot be an empty string or spaces")
    @NotNull(message = "the patronymic cannot be empty")
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
