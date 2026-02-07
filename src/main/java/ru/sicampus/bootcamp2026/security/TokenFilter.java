package ru.sicampus.bootcamp2026.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class TokenFilter extends OncePerRequestFilter {
    private final JwtConfig jwtConfig;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws IOException, ServletException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            log.debug("JWT token found: {}", jwt.substring(0, Math.min(jwt.length(), 20)) + "...");

            try {
                String username = jwtConfig.getUsernameFromToken(jwt);
                log.info("Username from token: {}", username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("Authentication successful for user: {}", username);
                }
            } catch (ExpiredJwtException e) {
                log.error("JWT token expired: {}", e.getMessage());
            } catch (JwtException e) {
                log.error("Invalid JWT token: {}", e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                log.error("Error processing JWT: {}", e.getMessage());
                e.printStackTrace();
            }
        }
        chain.doFilter(request, response);
    }
}