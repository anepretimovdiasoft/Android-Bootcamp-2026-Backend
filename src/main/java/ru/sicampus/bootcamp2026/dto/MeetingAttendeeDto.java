package ru.sicampus.bootcamp2026.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sicampus.bootcamp2026.model.enums.UserStatus;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class MeetingAttendeeDto {
    private Long userId;
    private String username;
    private UserStatus status;
}
