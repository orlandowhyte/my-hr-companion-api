package com.hr.companion.api.emergencycontact;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request Object for Emergency Contact")
public class EmergencyContactRequest {
    @Schema(description = "Employee who has this contact", example = "4423798d-6c20-4515-bfb3-9edaa50dfd88")
    private UUID employeeId;
    @Schema(description = "First name of emergency contact", example = "John")
    @NotBlank(message = "First name is required")
    private String firstName;
    @Schema(description = "Last name of emergency contact", example = "Doe")
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Schema(description = "Relationship to employee", example = "Brother")
    @NotBlank(message = "Relationship is required")
    private String relationship;
    @Schema(description = "Phone number of emergency contact", example = "+1-202-555-0143")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @Schema(description = "Email address of emergency contact", example = "contact@emergency.com")
    @Email(message = "Email should be valid")
    private String emailAddress;
    @Schema(description = "Is this the primary contact?", example = "true")
    private boolean isPrimary;
    private String status = "active";
}
