package ru.examle.edu.dto;


import lombok.Data;

@Data
public class PersonDTO {

    private long id;
    private String name;
    private String email;
    private String photoUrl;
    private String departmentName;

}
