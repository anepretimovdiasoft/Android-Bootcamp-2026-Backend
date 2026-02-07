package ru.sicampus.bootcamp2026.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.sicampus.bootcamp2026.exceptions.ExceptionBody;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class ApplicationConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Booking API")
                        .description("Booking API application")
                        .version("0.0.1")
                );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(httpBasic -> httpBasic.realmName("Booking API"))
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint((request, response, authException) -> {
                                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                    response.setContentType("application/json; charset=UTF-8");

                                    response.getWriter().write(new ObjectMapper().writeValueAsString(
                                            new ExceptionBody("Не авторизован.")
                                    ));
                                })
                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                    response.setStatus(HttpStatus.FORBIDDEN.value());
                                    response.setContentType("application/json; charset=UTF-8");

                                    response.getWriter().write(new ObjectMapper().writeValueAsString(
                                            new ExceptionBody("Нет прав администратора.")
                                    ));
                                })
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/h2-console/**").permitAll()  // убрать на проде
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()

                        .requestMatchers("/api/v1/auth/**").permitAll()
                        //.requestMatchers(HttpMethod.DELETE, "/api/v1/employee/{id}").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/v1/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                        .anyRequest().authenticated()
                )
                .anonymous(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

}
