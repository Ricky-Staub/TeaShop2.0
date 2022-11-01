package com.example.TeaShop2.domain.entitys.user.dto;

import com.example.TeaShop2.core.generic.ExtendedMapper;
import com.example.TeaShop2.domain.entitys.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends ExtendedMapper<User, UserDTO> {

    User fromUserRegisterDTO(UserRegisterDTO dto);
}