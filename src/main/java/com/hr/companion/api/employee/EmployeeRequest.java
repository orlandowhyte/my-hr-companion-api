package com.hr.companion.api.employee;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Request Object for Employee")
public class EmployeeRequest {
    @Schema(description = "", example = "")
    @NotBlank(message = "")
    private UUID id;
}
