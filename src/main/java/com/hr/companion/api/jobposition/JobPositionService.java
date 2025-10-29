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

    public JobPositionResponse createJobPosition(JobPositionRequest request) {
        log.info("Creating new job position with title: {}", request.getTitle());
        JobPosition savedJobPosition = jobPositionRepository.save(convertToJobPosition(request));
        log.info("Job position created with ID: {}", savedJobPosition.getJobPositionId());
        return convertToResponse(savedJobPosition);
    }

    public List<JobPositionResponse> getAllJobPositionsByDepartment(UUID departmentId) {
        log.info("Fetching all job positions for department with ID: {}", departmentId);
        List<JobPosition> jobPositions = jobPositionRepository.findJobPositionsByDepartmentId(departmentId);
        return jobPositions.stream()
                .map(this::convertToResponse)
                .toList();
    }

    public JobPosition convertToJobPosition(JobPositionRequest request) {
        return JobPosition.builder()
                .departmentId(request.getDepartmentId())
                .title(request.getTitle())
                .jobDescription(request.getJobDescription())
                .build();
    }

    public JobPositionResponse convertToResponse(JobPosition jobPosition) {
        return JobPositionResponse.builder()
                .jobPositionId(jobPosition.getJobPositionId())
                .departmentId(jobPosition.getDepartmentId())
                .title(jobPosition.getTitle())
                .jobDescription(jobPosition.getJobDescription())
                .status(jobPosition.getStatus())
                .createdAt(jobPosition.getCreatedAt())
                .updatedAt(jobPosition.getUpdatedAt())
                .build();
    }
}
