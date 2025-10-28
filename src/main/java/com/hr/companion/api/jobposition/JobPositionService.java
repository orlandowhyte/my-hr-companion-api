package com.hr.companion.api.jobposition;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class JobPositionService {
    private final JobPositionRepository jobPositionRepository;
    private final JobPositionMapper jobPositionMapper;

    /**
     * Creates a new job position.
     * @param jobPositionDTO the job position data transfer object
     * @return the created job position DTO
     */
    public JobPositionDTO createJobPosition(JobPositionDTO jobPositionDTO) {
        log.info("Creating new job position with title: {}", jobPositionDTO.getTitle());
        JobPosition savedJobPosition = jobPositionRepository.save(jobPositionMapper.toEntity(jobPositionDTO));
        log.info("Job position created with ID: {}", savedJobPosition.getJobPositionId());
        return jobPositionMapper.toDto(savedJobPosition);
    }
}
