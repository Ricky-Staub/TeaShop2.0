package com.example.TeaShop2.domain.entitys.authority.dto;

import com.example.TeaShop2.core.generic.ExtendedMapper;
import com.example.TeaShop2.domain.entitys.authority.Authority;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorityMapper extends ExtendedMapper<Authority, AuthorityDTO> {
}

