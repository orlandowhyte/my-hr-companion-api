package com.hr.companion.api.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(description = "Request object for creating a new user")
public class RegisterRequest {
    @Schema(description = "Unique username for user", example = "johndoe")
    @NotBlank(message = "Username is required")
    private String username;
    @Schema(description = "First name for user", example = "john")
    @NotBlank(message = "First name is required")
    private String firstname;
    @Schema(description = "Last name for user", example = "doe")
    @NotBlank(message = "Last name is required")
    private String lastname;
    @Schema(description = "Password to authenticate user", example = "weird^&password123")
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    @Schema(description = "Unique email for user", example = "johndoe@gmail.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
}
