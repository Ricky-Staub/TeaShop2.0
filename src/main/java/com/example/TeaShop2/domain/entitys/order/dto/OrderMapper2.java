package com.example.TeaShop2.domain.entitys.order.dto;

import com.example.TeaShop2.core.generic.ExtendedMapper;
import com.example.TeaShop2.domain.entitys.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper2 extends ExtendedMapper<Order, OrderDTO> {
    List<OrderDTO> fromOrderToDTO(List<Order> orders);
}