package com.hr.companion.api.auth;

import com.hr.companion.api.security.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/v1/api/auth/")
@AllArgsConstructor
public class UserController {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse res) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        var user = userDetailsService.loadUserByUsername(request.getUsername());
        var accessToken = jwtService.generateAccessToken(
                user.getUsername(),
                user.getAuthorities()
        );
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

        return ResponseEntity.ok(Map.of("accessToken", accessToken));
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {

        String username = body.get("username");
        String email = body.get("email");
        String password = body.get("password");

        // Check duplicates
        if (userDetailsService.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username already taken"));
        }

        // Hash password
        String encodedPassword = passwordEncoder.encode(password);

        // Default role
        Set<String> roles = Set.of("USER");

        // Create user
        User newUser = User.builder()
                .username(username)
                .email(email)
                .password(encodedPassword)
                .roles(roles)
                .enabled(true)
                .build();

        userRepository.save(newUser);

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }
}
