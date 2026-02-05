package ru.sicampus.bootcamp2026.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.sicampus.bootcamp2026.dtos.SigninDto;
import ru.sicampus.bootcamp2026.dtos.SignupDto;
import ru.sicampus.bootcamp2026.entities.User;
import ru.sicampus.bootcamp2026.repositories.UserRepository;
import ru.sicampus.bootcamp2026.security.JwtConfig;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupDto signupRequest){
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            log.error("Email already exists");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Email already exists, choose another one");
        }
        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setHash_password(passwordEncoder.encode(signupRequest.getPassword()));
        user.setPosition(signupRequest.getPosition()); // <-- Добавь эту строку
        user.setPhotoUrl(signupRequest.getPhotoUrl());userRepository.save(user);
        log.info("User was signed up");
        return ResponseEntity.ok("success");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninDto signinRequest){
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signinRequest.getEmail(),
                            signinRequest.getPassword()
                    )
            );
            String token = jwtConfig.generateToken(authentication);
            return ResponseEntity.ok(token);

        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String > handleValidationException(MethodArgumentNotValidException ex){
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
        });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors.toString());
    }
}
