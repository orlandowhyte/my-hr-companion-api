package com.hr.companion.api.jobposition;

import com.hr.companion.api.department.Department;
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
@Schema(description = "Data Transfer Object for Job Position entity")
public class JobPositionDTO {
    @Schema(description = "Unique Id for job description", example = "3663798d-6c20-4515-bfb3-9edaa50dfd88")
    private UUID jobPositionId;
    @Schema(description = "Foreign key Id for department", example = "4423798d-6c20-4515-bfb3-9edaa50dfd88")
    private UUID departmentId;
    @Schema(description = "Job title", example = "Software Engineer")
    @NotBlank(message = "Job title is required")
    private String title;
    @Schema(description = "Job description", example = "Responsible for developing and maintaining software.")
    private String jobDescription;
    @Schema(description = "Status of job position", example = "active")
    private String status = "active";
    @Schema(description = "Creation date of job position", example = "2025-10-25 21:45:13.123+00")
    private OffsetDateTime createdAt;
    @Schema(description = "Update date of department", example = "2025-10-25 21:45:13.123+00")
    private OffsetDateTime updatedAt;
}
