package com.hr.companion.api.emergencycontact;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "emergency_contact")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyContact {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "contact_id", nullable = false, updatable = false)
    private UUID contactId;
    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    private String relationship;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "email_address", nullable = false)
    private String emailAddress;
    @Column(name = "is_primary", nullable = false)
    private boolean isPrimary;
    private String status = "active";
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
