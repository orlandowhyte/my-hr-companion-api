package com.hr.companion.api.auth;

import com.hr.companion.api.exception.UserAlreadyExistsException;
import com.hr.companion.api.security.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Fetching details for user: {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    /**
     * Authenticates a user by their username and password.
     * @param username The username of the user to authenticate.
     * @param password The password of the user to authenticate.
     * @return A JWT token if authentication is successful.
     */
    public String authenticateUser(String username, String password, HttpServletResponse res) {
        log.info("Authenticating user: {}", username);
        var user = loadUserByUsername(username);
        var accessToken = jwtService.generateAccessToken(user.getUsername(), user.getAuthorities());
        var refreshToken = jwtService.generateRefreshToken(user.getUsername());
        // Set refresh token cookie (HttpOnly)
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .build();
        res.addHeader("Set-Cookie", cookie.toString());
        log.info("User: {} Authenticated successfully with token: {}", username, accessToken);
        return accessToken;
    }


    /**
     * Registers a new user in the system.
     * @param request request containing user details to be saved.
     * @return The registered user entity.
     */
    public User registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            log.error("Account already exists with username: {}", request.getUsername());
            throw new UserAlreadyExistsException("Username already taken: " + request.getUsername());
        }
        // Hash password
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        // Default role
        Set<String> roles = Set.of("USER");
        // Create user
        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encodedPassword)
                .roles(roles)
                .enabled(true)
                .build();
        userRepository.save(newUser);
        log.info("User: {} created successfully", request.getUsername());
        return newUser;
    }
}
