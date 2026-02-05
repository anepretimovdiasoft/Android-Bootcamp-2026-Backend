package ru.sicampus.bootcamp2026.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.sicampus.bootcamp2026.Entity.Contact;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GetEmployeeResponse {
    private String mail;
    private  String name;
    private String last_name;
    private String father_name;
    private String avatar;
    private List<String> contacts;

    public GetEmployeeResponse(String name, String lastName, String fatherName, String mail, List<String> contacts,String avatar) {
        this.name=name;
        this.last_name=lastName;
        this.father_name=fatherName;
        this.mail=mail;
        this.contacts=contacts;
        this.avatar=avatar;
    }
}
