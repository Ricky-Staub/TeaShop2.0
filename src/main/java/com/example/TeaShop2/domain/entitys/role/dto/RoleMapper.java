package com.example.TeaShop2.domain.entitys.role.dto;

import com.example.TeaShop2.core.generic.ExtendedMapper;
import com.example.TeaShop2.domain.entitys.role.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends ExtendedMapper<Role, RoleDTO> {
}
