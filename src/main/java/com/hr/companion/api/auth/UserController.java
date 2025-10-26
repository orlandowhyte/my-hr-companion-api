package com.hr.companion.api.auth;

import com.hr.companion.api.util.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/api/auth/")
@AllArgsConstructor
@Tag(name="User Management", description="Controller that handles user authentication and registration")
public class UserController {
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authManager;

    @PostMapping("login")
    @Operation(summary = "Login for users", description = "Authenticates user and returns JWT token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User account is disabled or locked"),
            @ApiResponse(responseCode = "500", description = "Internal server error during authentication")
    })
    public ResponseEntity<ApiSuccessResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request, HttpServletResponse res) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String accessToken = userDetailsService.authenticateUser(request.getUsername(), res);
        return ResponseEntity.ok((ApiSuccessResponse.success(new LoginResponse(accessToken),
                "User successfully authenticated", HttpStatus.OK.value())));
    }

    @PostMapping("register")
    @Operation(summary = "Create a new user", description = "Creates a new user account in the system")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "User already exists or invalid data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error during user registration")
    })
    public ResponseEntity<ApiSuccessResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {
        User newUser = userDetailsService.registerUser(request);
        URI location = URI.create("/v1/api/auth/register/");
        var response = RegisterResponse.builder()
                .id(newUser.getId())
                .username(newUser.getUsername())
                .firstname(newUser.getFirstname())
                .lastname(newUser.getLastname())
                .email(newUser.getEmail())
                .enabled(newUser.isEnabled())
                .roles(newUser.getRoles())
                .createdAt(newUser.getCreatedAt())
                .updatedAt(newUser.getUpdatedAt())
                .status(newUser.getStatus())
                .accountNonLocked(newUser.isAccountNonLocked())
                .accountNonExpired(newUser.isAccountNonExpired())
                .credentialsNonExpired(newUser.isCredentialsNonExpired())
                .build();
        return ResponseEntity.created(location).body((ApiSuccessResponse.success(response,
                "User created successfully", HttpStatus.CREATED.value())));
    }
}
