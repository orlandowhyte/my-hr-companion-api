package com.hr.companion.api.jobposition;

import com.hr.companion.api.config.CentralMapperConfig;
import com.hr.companion.api.util.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(config = CentralMapperConfig.class)
public interface JobPositionMapper extends BaseMapper<JobPosition, JobPositionDTO> {
}
