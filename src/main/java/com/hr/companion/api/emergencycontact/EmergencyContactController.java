package com.hr.companion.api.emergencycontact;

import com.hr.companion.api.util.ApiErrorResponse;
import com.hr.companion.api.util.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/emergency-contact")
@AllArgsConstructor
@Tag(name="Emergency Contact", description="Controller that handles emergency contact operations")
public class EmergencyContactController {
    private final EmergencyContactService emergencyContactService;

    @GetMapping
    @Operation(summary = "Return all emergency contacts", description = "Returns a list of all emergency contacts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Emergency contacts retrieved successfully"),
    })
    public ResponseEntity<ApiSuccessResponse<List<EmergencyContactResponse>>> getAllEmergencyContacts() {
        List<EmergencyContactResponse> emergencyContacts = emergencyContactService.getAllEmergencyContacts();
        return ResponseEntity.ok((ApiSuccessResponse.success(emergencyContacts,
                "List of emergency contacts returned successfully", HttpStatus.OK.value())));
    }

    @PostMapping
    @Operation(summary = "Create a new emergency contact", description = "Creates a new emergency contact with the provided details")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Emergency contact created successfully"),
    })
    public ResponseEntity<ApiSuccessResponse<EmergencyContactResponse>> createEmergencyContact(
            @Valid @RequestBody EmergencyContactRequest request) {
        URI location = URI.create("/v1/api/emergency-contact");
        EmergencyContactResponse createdEmergencyContact =  emergencyContactService.createEmergencyContact(request);
        return ResponseEntity.created(location).body((ApiSuccessResponse.success(createdEmergencyContact,
                "Emergency contact created successfully", HttpStatus.CREATED.value())));
    }

    @GetMapping("/{emergencyContactId}")
    @Operation(summary = "Return emergency contact by Id", description = "Finds and returns emergency contact by Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Emergency contact found successfully"),
            @ApiResponse(responseCode = "404", description = "Emergency contact not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    public ResponseEntity<ApiSuccessResponse<EmergencyContactResponse>> getEmergencyContactById(
            @PathVariable("emergencyContactId") String emergencyContactId) {
        EmergencyContactResponse emergencyContact = emergencyContactService.getEmergencyContactById(UUID.fromString(emergencyContactId));
        return ResponseEntity.ok((ApiSuccessResponse.success(emergencyContact,
                "Emergency contact returned successfully", HttpStatus.OK.value())));
    }
}
