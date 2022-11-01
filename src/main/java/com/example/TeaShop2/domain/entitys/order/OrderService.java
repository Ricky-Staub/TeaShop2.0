package com.example.TeaShop2.domain.entitys.order;

import com.example.TeaShop2.core.generic.ExtendedService;
import com.example.TeaShop2.domain.entitys.order.dto.OrderCountDTO;
import com.example.TeaShop2.domain.entitys.order.dto.OrderCreateDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService extends ExtendedService<Order> {

    @Transactional
    Order createOrder(Order order);

    List<Order> findOwn();

    List<OrderCountDTO>findTeas();

    List<OrderCreateDTO> getAllOrdersPage(Integer pageNo, Integer pageSize);
}