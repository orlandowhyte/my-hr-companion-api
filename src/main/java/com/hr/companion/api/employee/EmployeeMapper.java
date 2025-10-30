package com.hr.companion.api.employee;

import com.hr.companion.api.config.CentralMapperConfig;
import com.hr.companion.api.util.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(config = CentralMapperConfig.class)
public interface EmployeeMapper extends BaseMapper<Employee, EmployeeRequest, EmployeeResponse>{}
