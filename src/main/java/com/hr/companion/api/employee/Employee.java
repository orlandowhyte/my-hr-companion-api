package com.hr.companion.api.employee;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "employee")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;
    @Column(name = "department_id", nullable = false)
    private UUID departmentId;
    @Column(name = "job_position_id", nullable = false)
    private UUID jobPositionId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email_address", nullable = false)
    private String emailAddress;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;
    private String gender;
    @Column(name = "address_line_1", nullable = false)
    private String addressLine1;
    @Column(name = "address_line_2")
    private String addressLine2;
    @Column(name = "apartment_number")
    private String apartmentNumber;
    private String city;
    private String parish;
    private String trn;
    private String nis;
    @Column(name = "start_date")
    private String startDate;
    @Column(name = "end_date")
    private String endDate;
    @Column(name = "employment_status", nullable = false)
    private String employmentStatus = "employed";
    @Column(name = "employment_type", nullable = false)
    private String employmentType;
    private String status = "active";
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
