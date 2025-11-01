package com.hr.companion.api.auth;

import com.hr.companion.api.util.ApiErrorResponse;
import com.hr.companion.api.util.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/api/auth/")
@AllArgsConstructor
@Tag(name="User", description="Controller that handles user authentication and registration")
public class UserController {
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authManager;

    @PostMapping("login")
    @Operation(summary = "Login for users", description = "Authenticates user and returns JWT token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - User account is disabled or locked",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error during authentication",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<ApiSuccessResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request, HttpServletResponse res) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        LoginResponse response = userDetailsService.authenticateUser(request.getUsername(), res);
        return ResponseEntity.ok((ApiSuccessResponse.success(response,
                "User successfully authenticated", HttpStatus.OK.value())));
    }

    @PostMapping("register")
    @Operation(summary = "Create a new user", description = "Creates a new user account in the system")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "User already exists or invalid data provided",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error during user registration",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    public ResponseEntity<ApiSuccessResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = userDetailsService.registerUser(request);
        URI location = URI.create("/v1/api/auth/register/");
        return ResponseEntity.created(location).body((ApiSuccessResponse.success(response,
                "User created successfully", HttpStatus.CREATED.value())));
    }

    @PostMapping("refresh")
    public ResponseEntity<ApiSuccessResponse<LoginResponse>> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        LoginResponse response = userDetailsService.refreshAccessToken(refreshToken);
        if(response == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    ApiSuccessResponse.success(null,
                            "Invalid or expired refresh token", HttpStatus.UNAUTHORIZED.value()));
        }

        return ResponseEntity.ok((ApiSuccessResponse.success(response,
                "Access token refreshed successfully", HttpStatus.OK.value())));
    }

    @PostMapping("logout")
    public ResponseEntity<ApiSuccessResponse<LoginResponse>> logout(HttpServletResponse res) {
        LoginResponse response = userDetailsService.logoutUser(res);
        return ResponseEntity.ok((ApiSuccessResponse.success(response,
                "User logged out successfully", HttpStatus.OK.value())));
    }
}
