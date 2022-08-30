package com.example.jwt.domain.orderposition.dto;

import com.example.jwt.domain.orderposition.OrderPosition;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderPositionMapper {
    OnlyAmountDTO orderpositionToOnlyAmountDTO(OrderPosition orderPosition);
}
