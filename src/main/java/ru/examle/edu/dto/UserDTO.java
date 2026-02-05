package ru.examle.edu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.examle.edu.entity.enums.UserRole;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class UserDTO {
    private Long id;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordHash; 
    
    private String fullName;
    private String position;
    private String department;
    private String avatarUrl;
    private UserRole role;
    private String notificationSettings;
    private LocalTime workHoursStart;
    private LocalTime workHoursEnd;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
