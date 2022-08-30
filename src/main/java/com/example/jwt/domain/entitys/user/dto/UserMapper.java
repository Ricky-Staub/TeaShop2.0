package com.example.jwt.domain.entitys.user.dto;

import com.example.jwt.core.generic.ExtendedMapper;
import com.example.jwt.domain.entitys.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends ExtendedMapper<User, UserDTO> {

    User fromUserRegisterDTO(UserRegisterDTO dto);
}