package com.teto.planner.mapper;

import com.teto.planner.dto.LoadStatus;
import com.teto.planner.dto.UserDto;
import com.teto.planner.dto.UserMeDto;
import com.teto.planner.dto.UserSummaryDto;
import com.teto.planner.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(UserEntity user) {
        return new UserDto(
                user.getId(),
                user.getLogin(),
                user.getName(),
                user.getTelegramNick(),
                avatarUrl(user),
                user.getAvatarContentType(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public UserMeDto toMe(UserEntity user) {
        return new UserMeDto(
                user.getId(),
                user.getLogin(),
                user.getName(),
                user.getTelegramNick(),
                avatarUrl(user),
                user.getAvatarContentType(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public UserSummaryDto toSummary(UserEntity user, Integer busyHours, LoadStatus loadStatus) {
        return new UserSummaryDto(
                user.getId(),
                user.getLogin(),
                user.getName(),
                user.getTelegramNick(),
                busyHours,
                loadStatus,
                avatarUrl(user)
        );
    }

    private String avatarUrl(UserEntity user) {
        if (user.getAvatarBytes() == null || user.getAvatarBytes().length == 0) {
            return null;
        }
        return "/api/users/" + user.getId() + "/avatar";
    }
}
