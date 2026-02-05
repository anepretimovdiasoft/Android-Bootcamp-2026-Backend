package ru.sicampus.bootcamp2026.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sicampus.bootcamp2026.dto.fromApp.NewUserDTO;
import ru.sicampus.bootcamp2026.dto.toApp.UserDTO;
import ru.sicampus.bootcamp2026.entity.LoginData;
import ru.sicampus.bootcamp2026.entity.User;

@UtilityClass
public class UserMapper {
    public UserDTO convertToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getLoginData().getEmail());
        userDTO.setFullName(user.getFullName());
//        userDTO.setPhotoUrl(user.getPhotoUrl);

        return userDTO;
    }

    public User convertToDomain(NewUserDTO dto, PasswordEncoder passwordEncoder) {
        LoginData loginData = new LoginData();
        loginData.setEmail(dto.getEmail());
        loginData.setPassword(passwordEncoder.encode(dto.getPassword()));

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setLoginData(loginData);

        return user;
    }
}
