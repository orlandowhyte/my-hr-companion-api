package com.hr.companion.api.department;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

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
    public List<DepartmentDTO> getAllDepartments() {
        log.info("Returned {} departments", departmentRepository.count());
        return departmentMapper.toDtoList(departmentRepository.findAll());
    }

    /**
     * Creates a new department in the database.
     * @param request DepartmentDTO containing details of the department to be created.
     * @return DepartmentDTO representing the newly created department.
     */
    public DepartmentDTO createDepartment(DepartmentDTO request) {
        log.info("Creating a new department with name: {}", request.getDepartmentName());
        Department savedDepartment = departmentRepository.save(departmentMapper.toEntity(request));
        log.info("Department created with ID: {}", savedDepartment.getDepartmentId());
        return departmentMapper.toDto(savedDepartment);
    }
}
