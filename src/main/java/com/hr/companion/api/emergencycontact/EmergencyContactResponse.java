package com.hr.companion.api.emergencycontact;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response Object for Emergency Contact")
public class EmergencyContactResponse {
    @Schema(description = "Unique identifier for the emergency contact", example = "c1a2b3d4-e5f6-7a8b-9c0d-e1f2a3b4c5d6")
    private UUID contactId;
    @Schema(description = "Employee who has this contact", example = "4423798d-6c20-4515-bfb3-9edaa50dfd88")
    private UUID employeeId;
    @Schema(description = "First name of emergency contact", example = "John")
    private String firstName;
    @Schema(description = "Last name of emergency contact", example = "Doe")
    private String lastName;
    @Schema(description = "Relationship to employee", example = "Brother")
    private String relationship;
    @Schema(description = "Phone number of emergency contact", example = "+1-202-555-0143")
    private String phoneNumber;
    @Schema(description = "Email address of emergency contact", example = "contact@emergency.com")
    private String emailAddress;
    @Schema(description = "Is this the primary contact?", example = "true")
    private boolean isPrimary;
    @Schema(description = "Status of emergency contact", example = "active")
    private String status = "active";
    @Schema(description = "Creation date of emergency contact", example = "2025-10-25 21:45:13.123+00")
    private OffsetDateTime createdAt;
    @Schema(description = "Update date of emergency contact", example = "2025-10-25 21:45:13.123+00")
    private OffsetDateTime updatedAt;
}
