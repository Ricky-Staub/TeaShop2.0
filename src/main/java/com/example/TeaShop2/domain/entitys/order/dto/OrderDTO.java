package com.example.TeaShop2.domain.entitys.order.dto;

import com.example.TeaShop2.core.generic.ExtendedDTO;
import com.example.TeaShop2.domain.entitys.user.dto.UserDTO;
import com.example.TeaShop2.domain.orderposition.dto.OrderPositionDTO;

import java.util.Set;
import java.util.UUID;

public class OrderDTO extends ExtendedDTO {

    private float price;

    private UserDTO user;

    private Set<OrderPositionDTO> orderPositions;

    private Integer amount;
    public OrderDTO() {
    }

    public OrderDTO(UUID id, float price, UserDTO user, Set<OrderPositionDTO> orderPositions, Integer amount) {
        super(id);
        this.price = price;
        this.user = user;
        this.orderPositions = orderPositions;
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public OrderDTO setPrice(float price) {
        this.price = price;
        return this;
    }

    public UserDTO getUser() {
        return user;
    }

    public OrderDTO setUser(UserDTO user) {
        this.user = user;
        return this;
    }

    public Set<OrderPositionDTO> getOrderPositions() {
        return orderPositions;
    }

    public OrderDTO setOrderPositions(Set<OrderPositionDTO> orderPositions) {
        this.orderPositions = orderPositions;
        return this;
    }

    public Integer getAmount() {
        return amount;
    }

    public OrderDTO setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }
}
