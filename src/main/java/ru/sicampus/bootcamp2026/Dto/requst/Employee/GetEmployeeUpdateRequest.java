package ru.sicampus.bootcamp2026.Dto.requst.Employee;

import jakarta.validation.constraints.*;
import ru.sicampus.bootcamp2026.Entity.Avatar;

public class GetEmployeeUpdateRequest {
    private String name;
    private String last_name;
    private  String father_name;
    private  String mail;
    @Size(min = 8,max = 20)
    private  String password;
    @Min(value = 18)
    @Positive
    private Long age;
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
