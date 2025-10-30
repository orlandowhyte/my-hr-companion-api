package com.hr.companion.api.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request Object for Employee")
public class EmployeeRequest {
    @Schema(description = "Department of employee", example = "4423798d-6c20-4515-bfb3-9edaa50dfd88")
    private UUID departmentId;
    @Schema(description = "Job position of employee", example = "4423798d-6c20-4515-bfb3-9edaa50dfd88")
    private UUID jobPositionId;
    @Schema(description = "First name", example = "Jane")
    @NotBlank(message = "First name is required")
    private String firstName;
    @Schema(description = "Middle name", example = "A.")
    private String middleName;
    @Schema(description = "Last name", example = "Doe")
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Schema(description = "Email address", example = "janedoe@company.com")
    @NotBlank(message = "Email address is required")
    @Email(message = "Email address is not valid")
    private String emailAddress;
    @Schema(description = "Phone number", example = "+1-202-555-0143")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @Schema(description = "Date of birth", example = "1990-05-15")
    private Date dateOfBirth;
    @Schema(description = "Gender", example = "Female")
    @NotBlank(message = "Gender is required")
    private String gender;
    @Schema(description = "Address line 1", example = "123 Main St")
    @NotBlank(message = "Address line 1 is required")
    private String addressLine1;
    @Schema(description = "Address line 2", example = "Suite 4B")
    private String addressLine2;
    @Schema(description = "Apartment number", example = "4B")
    private String apartmentNumber;
    @Schema(description = "City", example = "Liguanea")
    private String city;
    @Schema(description = "Parish", example = "St. Andrew")
    @NotBlank(message = "Parish is required")
    private String parish;
    @Schema(description = "Tax Registration Number", example = "123-456-789")
    @NotBlank(message = "TRN is required")
    private String trn;
    @Schema(description = "National Insurance Scheme number", example = "X87-654-321")
    @NotBlank(message = "NIS is required")
    private String nis;
    @Schema(description = "Start date of employment", example = "2022-01-10")
    @NotBlank(message = "Start date is required")
    private String startDate;
    @Schema(description = "Status of employment", example = "employed")
    @NotBlank(message = "Employment status is required")
    private String employmentStatus = "employed";
    @Schema(description = "Type of employment", example = "full-time")
    @NotBlank(message = "Employment type is required")
    private String employmentType;
    private String status = "active";
}
