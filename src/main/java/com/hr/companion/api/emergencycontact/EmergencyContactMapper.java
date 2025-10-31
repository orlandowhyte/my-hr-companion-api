package com.hr.companion.api.emergencycontact;

import com.hr.companion.api.config.CentralMapperConfig;
import com.hr.companion.api.util.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(config = CentralMapperConfig.class)
public interface EmergencyContactMapper extends BaseMapper<EmergencyContact, EmergencyContactRequest, EmergencyContactResponse>{}
