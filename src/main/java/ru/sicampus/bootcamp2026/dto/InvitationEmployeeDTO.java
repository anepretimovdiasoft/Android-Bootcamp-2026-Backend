package ru.sicampus.bootcamp2026.dto;

import lombok.Data;


@Data
public class InvitationEmployeeDTO {
    private Long Id;
    private String status;
    private EmployeeDTO employee;
}
