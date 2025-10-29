package com.hr.companion.api.department;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request Object for Department")
public class DepartmentRequest {
    @Schema(description = "Department name", example = "Technology")
    @NotBlank(message = "Department name is required")
    private String departmentName;
}
