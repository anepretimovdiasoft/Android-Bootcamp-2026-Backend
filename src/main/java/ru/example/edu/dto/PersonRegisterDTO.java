package ru.example.edu.dto;

import lombok.Data;

@Data
public class PersonRegisterDTO {
    private long id;
    private String name;
    private String login;
    private String password;
    private String departmentName;
}
