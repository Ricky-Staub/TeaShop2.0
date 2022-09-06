package com.example.jwt.domain.entitys.order;

import com.example.jwt.core.generic.ExtendedService;
import com.example.jwt.domain.entitys.order.dto.OrderCountDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService extends ExtendedService<Order> {


    @Transactional
    Order createOrder(Order order);

    List<Order> findOwn();

    List<OrderCountDTO>findTeas();

}
