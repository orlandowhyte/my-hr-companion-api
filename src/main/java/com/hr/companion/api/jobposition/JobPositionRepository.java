package com.hr.companion.api.jobposition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, UUID> {
}
