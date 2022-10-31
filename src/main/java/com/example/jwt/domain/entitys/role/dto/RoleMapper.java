package com.example.jwt.domain.entitys.role.dto;

import com.example.jwt.core.generic.ExtendedMapper;
import com.example.jwt.domain.entitys.role.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends ExtendedMapper<Role, RoleDTO> {
}
