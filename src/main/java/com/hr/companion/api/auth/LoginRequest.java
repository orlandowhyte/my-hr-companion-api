package com.hr.companion.api.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request object for user login")
public class LoginRequest {
    @Schema(description = "Unique username for user", example = "johndoe")
    @NotBlank(message = "Username is required")
    private String username;
    @Schema(description = "Password to authenticate user", example = "weird^&password123")
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}
