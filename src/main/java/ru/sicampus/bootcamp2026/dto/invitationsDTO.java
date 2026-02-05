package ru.sicampus.bootcamp2026.dto;

import lombok.Data;

@Data
public class invitationsDTO {
    private Long id;
    private Long meetId;
    private Long userId;
    private String meetTitle;
    private String userName;
    private String meetDate;
    private String meetTime;
}