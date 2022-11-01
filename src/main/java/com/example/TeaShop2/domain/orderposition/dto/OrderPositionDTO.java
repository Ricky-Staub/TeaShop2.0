package com.example.TeaShop2.domain.orderposition.dto;

import com.example.TeaShop2.core.generic.ExtendedDTO;
import com.example.TeaShop2.domain.entitys.order.dto.OrderCreateDTO;
import com.example.TeaShop2.domain.entitys.teas.dto.TeaDTO;

import javax.validation.constraints.NotNull;

public class OrderPositionDTO extends ExtendedDTO {

    private Integer amount;

    private TeaDTO tea;


    public Integer getAmount() {
        return amount;
    }

    public OrderPositionDTO setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public TeaDTO getTea() {
        return tea;
    }

    public OrderPositionDTO setTea(TeaDTO tea) {
        this.tea = tea;
        return this;
    }
}