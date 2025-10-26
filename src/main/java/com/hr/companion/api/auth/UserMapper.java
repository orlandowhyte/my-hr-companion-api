package com.hr.companion.api.auth;

import com.hr.companion.api.config.CentralMapperConfig;
import com.hr.companion.api.util.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(config = CentralMapperConfig.class)
public interface UserMapper extends BaseMapper<User, RegisterResponse> {
}
