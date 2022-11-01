package com.example.TeaShop2.domain.orderposition.dto;

import com.example.TeaShop2.domain.orderposition.OrderPosition;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderPositionMapper {
    OnlyAmountDTO orderpositionToOnlyAmountDTO(OrderPosition orderPosition);
}