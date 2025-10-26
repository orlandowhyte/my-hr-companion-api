package com.hr.companion.api.department;

import com.hr.companion.api.auth.LoginResponse;
import com.hr.companion.api.util.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/api/department")
@AllArgsConstructor
@Tag(name="Department", description="Controller that handles department operations")
public class DepartmentController {
    public final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<List<DepartmentDTO>>> getAllDepartments() {
        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok((ApiSuccessResponse.success(departments,
                "List of departments returned successfully", HttpStatus.OK.value())));
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<DepartmentDTO>> createDepartment(
            @Valid @RequestBody DepartmentDTO request) {
        URI location = URI.create("/v1/api/department/");
        DepartmentDTO createdDepartment =  departmentService.createDepartment(request);
        return ResponseEntity.created(location).body((ApiSuccessResponse.success(createdDepartment,
                "Department created successfully", HttpStatus.CREATED.value())));
    }
}
