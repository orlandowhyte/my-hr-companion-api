package com.hr.companion.api.emergencycontact;

import com.hr.companion.api.exception.EmergencyContactNotFoundException;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class EmergencyContactService {
    private final EmergencyContactRepository emergencyContactRepository;
    private final EmergencyContactMapper emergencyContactMapper;


    /**
     * Retrieves all emergencyContacts from the database.
     * @return List of EmergencyContacts.
     */
    public List<EmergencyContactResponse> getAllEmergencyContacts() {
        log.info("Returned {} emergency contacts", emergencyContactRepository.count());
        return emergencyContactMapper.toResponseList(emergencyContactRepository.findAll());
    }

    /**
     * Creates a new emergencyContact in the database.
     * @param request EmergencyContactRequest containing details of the emergencyContact to be created.
     * @return EmergencyContactResponse representing the newly created emergencyContact.
     */
    public EmergencyContactResponse createEmergencyContact(EmergencyContactRequest request) {
        log.info("Creating a new emergency contact with id");
        EmergencyContact savedEmergencyContact = emergencyContactRepository.save(emergencyContactMapper.toEntity(request));
        log.info("Emergency contact created with ID: {}", savedEmergencyContact.getContactId());
        return emergencyContactMapper.toResponse(savedEmergencyContact);
    }

    /**
     * Retrieves a emergencyContact by its ID.
     * @param emergencyContactId UUID representing the unique identifier of the emergencyContact.
     * @return EmergencyContactResponse representing the emergencyContact with the specified ID.
     * @throws EmergencyContactNotFoundException if the emergencyContact is not found.
     */
    public EmergencyContactResponse getEmergencyContactById(UUID emergencyContactId) {
        EmergencyContact emergencyContact = emergencyContactRepository.findById(emergencyContactId)
                .orElseThrow(() -> new EmergencyContactNotFoundException("EmergencyContact not found with ID: " + emergencyContactId));
        return emergencyContactMapper.toResponse(emergencyContact);
    }
}
