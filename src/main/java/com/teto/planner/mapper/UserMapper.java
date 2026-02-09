package com.teto.planner.mapper;

import com.teto.planner.dto.LoadStatus;
import com.teto.planner.dto.RoleDto;
import com.teto.planner.dto.UserDto;
import com.teto.planner.dto.UserMeDto;
import com.teto.planner.dto.UserSummaryDto;
import com.teto.planner.entity.RoleEntity;
import com.teto.planner.entity.UserEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final String baseUrl;

    public UserMapper(@Value("${app.base-url:https://teto-planner.fly.dev}") String baseUrl) {
        if (baseUrl == null || baseUrl.isBlank()) {
            this.baseUrl = "";
        } else if (baseUrl.endsWith("/")) {
            this.baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        } else {
            this.baseUrl = baseUrl;
        }
    }

    public UserDto toDto(UserEntity user) {
        return new UserDto(
                user.getId(),
                user.getLogin(),
                user.getName(),
                user.getTelegramNick(),
                user.getBio(),
                avatarUrl(user),
                user.getAvatarContentType(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public UserMeDto toMe(UserEntity user) {
        List<RoleDto> roles = user.getRoles().stream()
                .map(this::toRole)
                .collect(Collectors.toList());
        return new UserMeDto(
                user.getId(),
                user.getLogin(),
                user.getName(),
                user.getTelegramNick(),
                user.getBio(),
                avatarUrl(user),
                user.getAvatarContentType(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                roles
        );
    }

    public UserSummaryDto toSummary(UserEntity user, Integer busyHours, LoadStatus loadStatus) {
        return new UserSummaryDto(
                user.getId(),
                user.getLogin(),
                user.getName(),
                user.getTelegramNick(),
                user.getBio(),
                busyHours,
                loadStatus,
                avatarUrl(user)
        );
    }

    private RoleDto toRole(RoleEntity role) {
        return new RoleDto(role.getId(), role.getSlug(), role.getName());
    }

    private String avatarUrl(UserEntity user) {
        if (user.getAvatarBytes() == null || user.getAvatarBytes().length == 0) {
            return null;
        }
        return baseUrl + "/api/users/" + user.getId() + "/avatar";
    }
}
