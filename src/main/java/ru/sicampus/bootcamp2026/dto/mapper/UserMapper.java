package ru.sicampus.bootcamp2026.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.sicampus.bootcamp2026.dto.request.RegisterRequest;
import ru.sicampus.bootcamp2026.dto.request.UpdateAvatarRequest;
import ru.sicampus.bootcamp2026.dto.request.UserProfileRequest;
import ru.sicampus.bootcamp2026.dto.response.AuthResponse;
import ru.sicampus.bootcamp2026.dto.response.UserProfileResponse;
import ru.sicampus.bootcamp2026.model.User;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Данные профиля пользователя: avatar_url -> avatarUrl
    @Mapping(source = "avatar_url", target = "avatarUrl")
    UserProfileResponse toUserProfileResponse(User user);

    // Ответ при аутентификации/регистрации — упаковать user + токены
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    AuthResponse toAuthResponse(User user, String accessToken, String refreshToken, Instant accessTokenExpiresAt, Instant refreshTokenExpiresAt);

    // Подготовка объекта User до хеширования пароля и сохранения в БД.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hashedPassword", ignore = true)
    @Mapping(target = "avatar_url", ignore = true)
    @Mapping(target = "role", ignore = true)
    User fromRegisterRequest(RegisterRequest req);

    // Обновление полей профиля (in-place)
    void updateFromUserProfileRequest(@MappingTarget User user, UserProfileRequest req);

    // Обновление аватарки (in-place)
    @Mapping(source = "avatarUrl", target = "avatar_url")
    void updateAvatar(@MappingTarget User user, UpdateAvatarRequest req);
}
