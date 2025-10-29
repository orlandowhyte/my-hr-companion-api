package com.hr.companion.api.department;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response Object for Department")
public class DepartmentResponse {
    @Schema(description = "Unique Id for department", example = "3663798d-6c20-4515-bfb3-9edaa50dfd88")
    private UUID departmentId;
    @Schema(description = "Department name", example = "Technology")
    @NotBlank(message = "Department name is required")
    private String departmentName;
    @Schema(description = "Status of department", example = "active")
    private String status = "active";
    @Schema(description = "Creation date of department", example = "2025-10-25 21:45:13.123+00")
    private OffsetDateTime createdAt;
    @Schema(description = "Update date of department", example = "2025-10-25 21:45:13.123+00")
    private OffsetDateTime updatedAt;
}
