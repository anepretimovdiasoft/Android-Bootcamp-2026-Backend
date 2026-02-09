package ru.sicampus.bootcamp2026.dto;

import lombok.Data;
import ru.sicampus.bootcamp2026.entity.UsersMeetingStatus;

@Data
public class MemberDTO {
    private UserDTO member;
    private UsersMeetingStatus status;
}
