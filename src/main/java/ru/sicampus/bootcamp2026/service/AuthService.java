package ru.sicampus.bootcamp2026.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sicampus.bootcamp2026.domain.Authority;
import ru.sicampus.bootcamp2026.domain.ConfirmationToken;
import ru.sicampus.bootcamp2026.domain.User;
import ru.sicampus.bootcamp2026.dto.AuthDtos.*;
import ru.sicampus.bootcamp2026.dto.UserDtos.CreateUserRequest;
import ru.sicampus.bootcamp2026.error.UserAlreadyExistsException;
import ru.sicampus.bootcamp2026.error.UserNotFoundException;
import ru.sicampus.bootcamp2026.repo.AuthorityRepository;
import ru.sicampus.bootcamp2026.repo.ConfirmationTokenRepository;
import ru.sicampus.bootcamp2026.repo.UserRepository;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final ConfirmationTokenRepository tokenRepo;
    private final AuthorityRepository authorityRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Transactional
    public String register(CreateUserRequest req) {
        if (userRepo.getUserByLogin(req.login()).isPresent()) {
            throw new UserAlreadyExistsException("Email already taken");
        }

        Authority roleUser = authorityRepo.getAuthorityByAuthority("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = User.builder()
                .name(req.name())
                .login(req.login())
                .password(passwordEncoder.encode(req.password()))
                .position(req.position())
                .authorities(Set.of(roleUser))
                .enabled(false)
                .build();

        userRepo.save(user);

        ConfirmationToken token = new ConfirmationToken(user);
        tokenRepo.save(token);

        emailService.send(req.login(), "Confirm your email", "Your confirmation code is: " + token.getToken());

        return "Registration successful. Check your email.";
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmToken = tokenRepo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        if (confirmToken.getConfirmedAt() != null) {
            throw new RuntimeException("Email already confirmed");
        }

        if (confirmToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        confirmToken.setConfirmedAt(LocalDateTime.now());
        confirmToken.getUser().setEnabled(true);

        userRepo.save(confirmToken.getUser());
        tokenRepo.save(confirmToken);

        return "Confirmed!";
    }

    @Transactional
    public LoginResponse login(LoginRequest req) {
        User user = userRepo.getUserByLogin(req.login())
                .orElseThrow(() -> new UserNotFoundException("Bad credentials"));

        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new RuntimeException("Bad credentials");
        }

        if (!user.isEnabled()) {
            throw new RuntimeException("Account not verified. Check email.");
        }

        String token = Base64.getEncoder().encodeToString((req.login() + ":" + req.password()).getBytes());
        return new LoginResponse(token, user.getName(), user.getPosition(), user.getId());
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequest req) {
        User user = userRepo.getUserByLogin(req.email())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        ConfirmationToken token = new ConfirmationToken(user);
        tokenRepo.save(token);

        emailService.send(req.email(), "Reset Password", "Your reset token: " + token.getToken());
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest req) {
        ConfirmationToken token = tokenRepo.findByToken(req.token())
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = token.getUser();
        user.setPassword(passwordEncoder.encode(req.newPassword()));
        userRepo.save(user);

        tokenRepo.delete(token);
    }
}