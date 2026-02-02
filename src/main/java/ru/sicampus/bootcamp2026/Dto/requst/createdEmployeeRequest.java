package ru.sicampus.bootcamp2026.Dto.requst;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class createdEmployeeRequest {
    @NotNull(message = "")
    @NotBlank(message = "")
    private String name;
    @NotBlank(message = "")
    @NotNull(message = "")
    private String last_name;
    @NotBlank(message = "")
    @NotNull(message = "")
    private String father_name;
    @NotBlank(message = "")
    @NotNull(message = "")
    @Email(message = "")
    private String mail;
    @NotBlank(message = "")
    private String avatar;
    @NotNull(message = "")
    @Positive
    @Min(18)
    private int age;

    public String getMail() {
        return mail;
    }

    public Object getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFather_name() {
        return father_name;
    }
}
