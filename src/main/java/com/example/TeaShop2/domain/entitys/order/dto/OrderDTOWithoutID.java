package com.example.TeaShop2.domain.entitys.order.dto;

import com.example.TeaShop2.core.generic.ExtendedDTO;
import com.example.TeaShop2.domain.entitys.user.User;

import javax.swing.text.Position;

public class OrderDTOWithoutID {

    private User user;
    private Integer price;

    public OrderDTOWithoutID() {

    }


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
