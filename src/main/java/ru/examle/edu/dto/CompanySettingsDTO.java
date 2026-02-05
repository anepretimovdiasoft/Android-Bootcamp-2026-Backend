package ru.examle.edu.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompanySettingsDTO {
    private Long id;
    private String settingKey;
    private String settingValue;
    private String settingType;
    private String category;
    private String description;
    private boolean isEditable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
