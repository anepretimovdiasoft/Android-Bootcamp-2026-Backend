package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import ru.sicampus.bootcamp2026.dto.MemberDTO;
import ru.sicampus.bootcamp2026.dto.UserDTO;
import ru.sicampus.bootcamp2026.entity.UsersMeetingStatus;

@UtilityClass
public class MemberMapper {
    public MemberDTO convertToDto(UserDTO dto, UsersMeetingStatus status) {
        MemberDTO response = new MemberDTO();
        response.setMember(dto);
        response.setStatus(status);
        return response;
    }
}
