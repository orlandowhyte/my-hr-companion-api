package com.hr.companion.api.department;

import com.hr.companion.api.exception.DepartmentNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class DepartmentService {
    final private DepartmentRepository departmentRepository;
    final private DepartmentMapper departmentMapper;

    /**
     * Retrieves all departments from the database.
     * @return List of Departments representing all departments.
     */
    public List<DepartmentResponse> getAllDepartments() {
        log.info("Returned {} departments", departmentRepository.count());
        return departmentMapper.toResponseList(departmentRepository.findAll());
    }

    /**
     * Creates a new department in the database.
     * @param request DepartmentRequest containing details of the department to be created.
     * @return DepartmentResponse representing the newly created department.
     */
    public DepartmentResponse createDepartment(DepartmentRequest request) {
        log.info("Creating a new department with name: {}", request.getDepartmentName());
        Department savedDepartment = departmentRepository.save(departmentMapper.toEntity(request));
        log.info("Department created with ID: {}", savedDepartment.getDepartmentId());
        return departmentMapper.toResponse(savedDepartment);
    }

    /**
     * Retrieves a department by its ID.
     * @param departmentId UUID representing the unique identifier of the department.
     * @return DepartmentResponse representing the department with the specified ID.
     * @throws DepartmentNotFoundException if the department is not found.
     */
    public DepartmentResponse getDepartmentById(UUID departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + departmentId));
        return departmentMapper.toResponse(department);
    }

    /**
     * Retrieves a department by its name.
     * @param departmentName String representing the name of the department.
     * @return DepartmentResponse representing the department with the specified name.
     * @throws DepartmentNotFoundException if the department is not found.
     */
    public DepartmentResponse getDepartmentByName(String departmentName) {
        Department department = departmentRepository.findByDepartmentName(departmentName)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with name: " + departmentName));
        return departmentMapper.toResponse(department);
    }
}
