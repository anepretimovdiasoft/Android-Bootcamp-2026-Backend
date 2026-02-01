package ru.example.edu.dto;

import lombok.Data;

@Data
public class InviteWithPersonDTO {
    private long id;
    private Boolean agree;
    private PersonShortDTO participant;
}
