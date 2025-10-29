package com.hr.companion.api.jobposition;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request Object for Job Position")
public class JobPositionRequest {
    @Schema(description = "Foreign key Id for department", example = "4423798d-6c20-4515-bfb3-9edaa50dfd88")
    private UUID departmentId;
    @Schema(description = "Job title", example = "Software Engineer")
    @NotBlank(message = "Job title is required")
    private String title;
    @Schema(description = "Job description", example = "Responsible for developing and maintaining software.")
    private String jobDescription;
}
