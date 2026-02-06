package ru.sicampus.bootcamp2026.Dto.requst.Employee;

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
    @NotBlank
    private String avatar;

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFather_name() {
        return father_name;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatar;
    }
}
