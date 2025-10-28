package com.hr.companion.api.jobposition;

import com.hr.companion.api.department.DepartmentDTO;
import com.hr.companion.api.util.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/api/job-position")
@AllArgsConstructor
@Tag(name="Job Position", description="Controller that handles Job Position operations")
public class JobPositionController {
    private final JobPositionService jobPositionService;

    @PostMapping
    @Operation(summary = "Create a new position", description = "Creates a new job position")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Position created successfully"),
    })
    public ResponseEntity<ApiSuccessResponse<JobPositionDTO>> createJobPosition(
            @Valid @RequestBody JobPositionDTO request) {
        URI location = URI.create("/v1/api/job-position");
        JobPositionDTO createdJobPosition =  jobPositionService.createJobPosition(request);
        return ResponseEntity.created(location).body((ApiSuccessResponse.success(createdJobPosition,
                "Job position created successfully", 201)));
    }
}
