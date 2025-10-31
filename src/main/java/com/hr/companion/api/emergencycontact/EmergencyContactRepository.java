package com.hr.companion.api.emergencycontact;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, UUID> {}
