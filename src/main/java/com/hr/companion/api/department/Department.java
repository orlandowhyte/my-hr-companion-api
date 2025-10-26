package com.hr.companion.api.department;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "department_id")
    private UUID departmentId;
    @Column(name = "department_name")
    private String departmentName;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
}
