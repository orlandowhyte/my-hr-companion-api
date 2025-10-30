package com.hr.companion.api.employee;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Response Object for Employee")
public class EmployeeResponse {
    private UUID id;
    @Schema(description = "Status of employee", example = "active")
    private String status = "active";
    @Schema(description = "Creation date of employee", example = "2025-10-25 21:45:13.123+00")
    private OffsetDateTime createdAt;
    @Schema(description = "Update date of employee", example = "2025-10-25 21:45:13.123+00")
    private OffsetDateTime updatedAt;
}
