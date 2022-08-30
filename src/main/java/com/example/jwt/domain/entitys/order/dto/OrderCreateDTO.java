package com.example.jwt.domain.entitys.order.dto;

import com.example.jwt.core.generic.ExtendedDTO;
import com.example.jwt.domain.entitys.user.dto.UserDTO;
import com.example.jwt.domain.orderposition.dto.OrderPositionDTO;

import java.util.Set;
import java.util.UUID;

public class OrderCreateDTO extends ExtendedDTO {
    private UserDTO user;

    private Set<OrderPositionDTO> orderPositions;

    public OrderCreateDTO() {
    }

    public OrderCreateDTO(UUID id, UserDTO user, Set<OrderPositionDTO> orderPositions) {
        super(id);
        this.user = user;
        this.orderPositions = orderPositions;
    }

    public UserDTO getUser() {
        return user;
    }

    public OrderCreateDTO setUser(UserDTO user) {
        this.user = user;
        return this;
    }

    public Set<OrderPositionDTO> getOrderPositions() {
        return orderPositions;
    }

    public OrderCreateDTO setOrderPositions(Set<OrderPositionDTO> orderPositions) {
        this.orderPositions = orderPositions;
        return this;
    }
}