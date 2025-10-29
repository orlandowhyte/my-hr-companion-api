package com.hr.companion.api.jobposition;

import com.hr.companion.api.util.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<ApiSuccessResponse<JobPositionResponse>> createJobPosition(
            @Valid @RequestBody JobPositionRequest request) {
        URI location = URI.create("/v1/api/job-position");
        JobPositionResponse createdJobPosition =  jobPositionService.createJobPosition(request);
        return ResponseEntity.created(location).body((ApiSuccessResponse.success(createdJobPosition,
                "Job position created successfully", 201)));
    }

    @GetMapping("/department/{departmentId}")
    @Operation(summary = "Get all job positions by department",
            description = "Retrieves all job positions for a specific department")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Job positions retrieved successfully")
    })
    public ResponseEntity<ApiSuccessResponse<List<JobPositionResponse>>> getAllJobPositionsByDepartment(
            @PathVariable("departmentId") String departmentId) {
        List<JobPositionResponse> jobPositions = jobPositionService.getAllJobPositionsByDepartment(
                UUID.fromString(departmentId));
        return ResponseEntity.ok(ApiSuccessResponse.success(jobPositions,
                "Job positions retrieved successfully", 200));
    }
}
