package ru.sicampus.bootcamp2026.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sicampus.bootcamp2026.dto.request.RegisterRequest;
import ru.sicampus.bootcamp2026.dto.response.AuthResponse;
import ru.sicampus.bootcamp2026.model.User;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Собирает DTO ответа авторизации из пользователя и пары токенов.
     * Нужен, чтобы контроллер/сервис не заполнял поля руками.
     */
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    AuthResponse toAuthResponse(
            User user,
            String accessToken,
            String refreshToken,
            Instant accessTokenExpiresAt,
            Instant refreshTokenExpiresAt
    );

    /**
     * Подготавливает сущность User из запроса регистрации.
     * Пароль намеренно НЕ маппится: его нужно захешировать в сервисе и положить в hashedPassword.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hashedPassword", ignore = true)
    @Mapping(target = "avatarUrl", ignore = true)
    @Mapping(target = "role", ignore = true)
    User fromRegisterRequest(RegisterRequest req);
}
