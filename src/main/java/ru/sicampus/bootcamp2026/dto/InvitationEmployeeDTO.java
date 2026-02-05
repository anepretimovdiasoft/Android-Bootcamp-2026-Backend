package ru.sicampus.bootcamp2026.dto;

import lombok.Data;


@Data
public class InvitationEmployeeDTO {
    private Long id;
    private String status;
    private EmployeeDTO employee;
}
