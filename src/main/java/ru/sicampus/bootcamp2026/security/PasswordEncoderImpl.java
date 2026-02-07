package ru.sicampus.bootcamp2026.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderImpl implements PasswordEncoder {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String encode(CharSequence rawPassword) {
        if(rawPassword == null){
            throw new IllegalArgumentException("Password cannot be null");
        } else {
            return encoder.encode(rawPassword);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if(rawPassword == null || encodedPassword == null){
            return false;
        }
        return encoder.matches(rawPassword.toString(), encodedPassword);
    }
}
