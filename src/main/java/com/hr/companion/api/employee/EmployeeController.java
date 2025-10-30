package com.hr.companion.api.employee;

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
@RequestMapping("/v1/api/employee")
@AllArgsConstructor
@Tag(name="Employee", description="Controller that handles employee operations")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    @Operation(summary = "Return all employees", description = "Returns a list of all employees")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employees retrieved successfully"),
    })
    public ResponseEntity<ApiSuccessResponse<List<EmployeeResponse>>> getAllEmployees() {
        List<EmployeeResponse> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok((ApiSuccessResponse.success(employees,
                "List of employees returned successfully", HttpStatus.OK.value())));
    }

    @PostMapping
    @Operation(summary = "Create a new employee", description = "Creates a new employee with the provided details")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employee created successfully"),
    })
    public ResponseEntity<ApiSuccessResponse<EmployeeResponse>> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {
        URI location = URI.create("/v1/api/employee");
        EmployeeResponse createdEmployee =  employeeService.createEmployee(request);
        return ResponseEntity.created(location).body((ApiSuccessResponse.success(createdEmployee,
                "Employee created successfully", HttpStatus.CREATED.value())));
    }

    @GetMapping("/{employeeId}")
    @Operation(summary = "Return employee by Id", description = "Finds and returns employee by Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee found successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    public ResponseEntity<ApiSuccessResponse<EmployeeResponse>> getEmployeeById(
            @PathVariable("employeeId") String employeeId) {
        EmployeeResponse employee = employeeService.getEmployeeById(UUID.fromString(employeeId));
        return ResponseEntity.ok((ApiSuccessResponse.success(employee,
                "Employee returned successfully", HttpStatus.OK.value())));
    }
}
