package ru.sicampus.bootcamp2026.Dto.requst.Employee;

import jakarta.validation.constraints.*;
import ru.sicampus.bootcamp2026.Entity.Avatar;

public class GetEmployeeUpdateRequest {
    @NotBlank(message = "The name cannot be an empty string.")
    private String name;
    @NotBlank(message = "the last name cannot be an empty string.")
    private String last_name;
    @NotBlank(message = "a patronymic cannot be an empty string or spaces")
    private  String father_name;
    @NotBlank()
    @Email
    private  String mail;
    @NotBlank
    @Size(min = 8,max = 20)
    private  String password;
    @Min(value = 18)
    @Positive
    private Long age;
    @NotBlank()
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

    public Long getAge() {
        return age;
    }
}
