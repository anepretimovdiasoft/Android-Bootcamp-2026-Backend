package ru.sicampus.bootcamp2026.Dto.requst;

import jakarta.validation.constraints.*;

public class GetEmployeeUpdateRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String last_name;
    @NotBlank
    private  String father_name;
    @NotBlank
    private  String mail;
    @NotBlank
    @Size(min = 8,max = 20)
    private  String password;
    @Min(value = 18)
    @Positive
    private int age;

    public String getMail() {
        return mail;
    }
}
