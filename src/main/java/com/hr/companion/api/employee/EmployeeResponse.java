package com.hr.companion.api.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response Object for Employee")
public class EmployeeResponse {
    @Schema(description = "ID of employee", example = "4423798d-6c20-4515-bfb3-9edaa50dfd88")
    private UUID employeeId;
    @Schema(description = "Department of employee", example = "4423798d-6c20-4515-bfb3-9edaa50dfd88")
    private UUID departmentId;
    @Schema(description = "Job position of employee", example = "4423798d-6c20-4515-bfb3-9edaa50dfd88")
    private UUID jobPositionId;
    @Schema(description = "First name", example = "Jane")
    private String firstName;
    @Schema(description = "Middle name", example = "A.")
    private String middleName;
    @Schema(description = "Last name", example = "Doe")
    private String lastName;
    @Schema(description = "Email address", example = "janedoe@company.com")
    private String emailAddress;
    @Schema(description = "Phone number", example = "+1-202-555-0143")
    private String phoneNumber;
    @Schema(description = "Date of birth", example = "1990-05-15")
    private Date dateOfBirth;
    @Schema(description = "Gender", example = "Female")
    private String gender;
    @Schema(description = "Address line 1", example = "123 Main St")
    private String addressLine1;
    @Schema(description = "Address line 2", example = "Suite 4B")
    private String addressLine2;
    @Schema(description = "Apartment number", example = "4B")
    private String apartmentNumber;
    @Schema(description = "City", example = "Liguanea")
    private String city;
    @Schema(description = "Parish", example = "St. Andrew")
    private String parish;
    @Schema(description = "Tax Registration Number", example = "123-456-789")
    private String trn;
    @Schema(description = "National Insurance Scheme number", example = "X87-654-321")
    private String nis;
    @Schema(description = "Start date of employment", example = "2022-01-10")
    private String startDate;
    @Schema(description = "End date of employment", example = "2023-12-31")
    private String endDate;
    @Schema(description = "Employment status", example = "employed")
    private String employmentStatus = "employed";
    @Schema(description = "Employment type", example = "full-time")
    private String employmentType;
    @Schema(description = "Status of employee", example = "active")
    private String status = "active";
    @Schema(description = "Timestamp when the employee record was created", example = "2023-10-05T14:48:00.000Z")
    private OffsetDateTime createdAt;
    @Schema(description = "Timestamp when the employee record was last updated", example = "2023-10-10T09:15:30.000Z")
    private OffsetDateTime updatedAt;
}
