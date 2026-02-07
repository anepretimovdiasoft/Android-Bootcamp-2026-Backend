package ru.sicampus.bootcamp2026.Dto.requst.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
@AllArgsConstructor
public class GetYouResponse {
    private String name;
    private String last_name;
    private String father_name;
    private String mail;
    private  String password;
    private  String avatar;
    private Long age;
    private List<Map<String,String>> contact;

    public GetYouResponse(String name, String lastName, String fatherName, String mail, String password, String avatar, long age, List<Map<String, String>> contactList) {
        this.name=name;
        this.last_name=lastName;
        this.father_name=fatherName;
        this.mail=mail;
        this.password=password;
        this.age=age;
        this.avatar=avatar;
        this.contact=contactList;

    }
}
