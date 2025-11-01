package com.hr.companion.api.auth;

import com.hr.companion.api.exception.UserAlreadyExistsException;
import com.hr.companion.api.security.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Fetching details for user: {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    /**
     * Authenticates a user by their username and password.
     * @param username The username of the user to authenticate.
     * @return A JWT token if authentication is successful.
     */
    public LoginResponse authenticateUser(String username, HttpServletResponse res) {
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
        return new LoginResponse(accessToken);
    }


    /**
     * Registers a new user in the system.
     * @param request RegisterRequest containing user details to be saved.
     * @return The registered user response.
     */
    public RegisterResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            log.error("Account already exists with username: {}", request.getUsername());
            throw new UserAlreadyExistsException("Username already taken: " + request.getUsername());
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Set<String> roles = Set.of(request.getRole());
        User newUser = userMapper.toEntity(request);
        newUser.setPassword(encodedPassword);
        newUser.setRoles(roles);
        userRepository.save(newUser);
        log.info("User: {} created successfully", request.getUsername());
        return userMapper.toResponse(newUser);
    }

    /**
     * Refreshes the access token using the provided refresh token.
     * @param refreshToken The refresh token to validate and use for generating a new access token.
     * @return A new LoginResponse containing the new access token, or null if the refresh token is invalid.
     */
    public LoginResponse refreshAccessToken(String refreshToken) {
        log.info("Refreshing access token using refresh token");
        if (refreshToken == null || !jwtService.isValid(refreshToken)) {
            log.error("Invalid refresh token provided");
            return new LoginResponse(null);
        }

        String username = jwtService.extractUsername(refreshToken);
        var user = loadUserByUsername(username);
        log.info("Access token refreshed successfully for user: {}", username);
        var newAccessToken = jwtService.generateAccessToken(username,
                user.getAuthorities());
        return new LoginResponse(newAccessToken);
    }

    /**
     * Logs out the user by clearing the refresh token cookie.
     * @param res HttpServletResponse to add the cleared cookie.
     * @return A LoginResponse with null token indicating successful logout.
     */
    public LoginResponse logoutUser(HttpServletResponse res) {
        log.info("Logging out user");
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true).secure(true).sameSite("Strict")
                .path("/").maxAge(0).build();
        res.addHeader("Set-Cookie", cookie.toString());
        log.info("User logged out successfully");
        return new LoginResponse(null);
    }

}
