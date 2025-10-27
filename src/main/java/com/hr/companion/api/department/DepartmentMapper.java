package com.hr.companion.api.department;

import com.hr.companion.api.config.CentralMapperConfig;
import com.hr.companion.api.util.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(config = CentralMapperConfig.class)
public interface DepartmentMapper extends BaseMapper<Department, DepartmentDTO> {
}
