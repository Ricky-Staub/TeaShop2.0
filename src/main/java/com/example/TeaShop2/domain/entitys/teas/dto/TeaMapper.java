package com.example.TeaShop2.domain.entitys.teas.dto;

import com.example.TeaShop2.domain.entitys.teas.Tea;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeaMapper {

    TeaDTO teaToTeaDTOWithoutID(Tea tea);


}
