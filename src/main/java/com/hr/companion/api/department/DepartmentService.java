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

    public List<DepartmentDTO> getAllDepartments() {
        log.info("Fetching all departments from the database....");
        log.info("Returned {} departments", departmentRepository.count());
        return departmentMapper.toDtoList(departmentRepository.findAll());
    }

    public DepartmentDTO createDepartment(DepartmentDTO request) {
        log.info("Creating a new department with name: {}", request.getDepartmentName());
        Department department = departmentMapper.toEntity(request);
        Department savedDepartment = departmentRepository.save(department);
        log.info("Department created with ID: {}", savedDepartment.getDepartmentId());
        return departmentMapper.toDto(savedDepartment);
    }
}
