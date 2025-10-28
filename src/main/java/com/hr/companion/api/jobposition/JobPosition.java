package com.hr.companion.api.jobposition;

import com.hr.companion.api.department.Department;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "job_position")
public class JobPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "job_position_id")
    private UUID jobPositionId;
    @Column(name = "department_id")
    private UUID departmentId;
    private String title;
    @Column(name = "job_description")
    private String jobDescription;
    private String status = "active";
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
