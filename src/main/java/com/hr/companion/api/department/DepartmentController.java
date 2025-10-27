package com.hr.companion.api.department;

import com.hr.companion.api.util.ApiErrorResponse;
import com.hr.companion.api.util.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/department")
@AllArgsConstructor
@Tag(name="Department", description="Controller that handles department operations")
public class DepartmentController {
    public final DepartmentService departmentService;

    @GetMapping
    @Operation(summary = "Return all departments", description = "Returns a list of all departments")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departments retrieved successfully"),
    })
    public ResponseEntity<ApiSuccessResponse<List<DepartmentDTO>>> getAllDepartments() {
        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok((ApiSuccessResponse.success(departments,
                "List of departments returned successfully", HttpStatus.OK.value())));
    }

    @PostMapping
    @Operation(summary = "Create a new department", description = "Authenticates user and returns JWT token")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Department created successfully"),
    })
    public ResponseEntity<ApiSuccessResponse<DepartmentDTO>> createDepartment(
            @Valid @RequestBody DepartmentDTO request) {
        URI location = URI.create("/v1/api/department");
        DepartmentDTO createdDepartment =  departmentService.createDepartment(request);
        return ResponseEntity.created(location).body((ApiSuccessResponse.success(createdDepartment,
                "Department created successfully", HttpStatus.CREATED.value())));
    }

    @GetMapping("/{departmentId}")
    @Operation(summary = "Return department by Id", description = "Finds and returns department by Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Department found successfully"),
            @ApiResponse(responseCode = "404", description = "Department not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    public ResponseEntity<ApiSuccessResponse<DepartmentDTO>> getDepartmentById(
            @PathVariable("departmentId") String departmentId) {
        DepartmentDTO department = departmentService.getDepartmentById(UUID.fromString(departmentId));
        return ResponseEntity.ok((ApiSuccessResponse.success(department,
                "Department returned successfully", HttpStatus.OK.value())));
    }

    @GetMapping("/departmentName")
    @Operation(summary = "Return department by name", description = "Returns department details by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Department found successfully"),
            @ApiResponse(responseCode = "404", description = "Department not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    public ResponseEntity<ApiSuccessResponse<DepartmentDTO>> getDepartmentByName(
            @Param("departmentName") String departmentName) {
        DepartmentDTO department = departmentService.getDepartmentByName(departmentName);
        return ResponseEntity.ok((ApiSuccessResponse.success(department,
                "Department returned successfully", HttpStatus.OK.value())));
    }
}
