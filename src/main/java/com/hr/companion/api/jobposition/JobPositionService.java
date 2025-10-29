package com.hr.companion.api.jobposition;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class JobPositionService {
    private final JobPositionRepository jobPositionRepository;
    private final JobPositionMapper jobPositionMapper;

    public JobPositionResponse createJobPosition(JobPositionRequest request) {
        log.info("Creating new job position with title: {}", request.getTitle());
        JobPosition savedJobPosition = jobPositionRepository.save(jobPositionMapper.toEntity(request));
        log.info("Job position created with ID: {}", savedJobPosition.getJobPositionId());
        return jobPositionMapper.toResponse(savedJobPosition);
    }

    public List<JobPositionResponse> getAllJobPositionsByDepartment(UUID departmentId) {
        log.info("Fetching all job positions for department with ID: {}", departmentId);
        List<JobPosition> jobPositions = jobPositionRepository.findJobPositionsByDepartmentId(departmentId);
        return jobPositions.stream().map(jobPositionMapper::toResponse).toList();
    }
}
