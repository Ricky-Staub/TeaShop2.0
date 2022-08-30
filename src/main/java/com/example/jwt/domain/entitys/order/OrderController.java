package com.example.jwt.domain.entitys.order;

import com.example.jwt.domain.entitys.order.dto.OrderCountDTO;
import com.example.jwt.domain.entitys.order.dto.OrderCreateDTO;
import com.example.jwt.domain.entitys.order.dto.OrderDTOWithoutID;
import com.example.jwt.domain.entitys.order.dto.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    // HIER SPEICHERST DU EINE NEUE BESTELLUNG
    @PostMapping
    public ResponseEntity<OrderCreateDTO> save(@RequestBody @Valid OrderCreateDTO orderCreateDTO) {
        Order order = orderService.createOrder(orderMapper.fromDTO(orderCreateDTO));
        return new ResponseEntity<>(orderMapper.toDTO(order), HttpStatus.CREATED);
    }

    @GetMapping
    public List<OrderCreateDTO> findOwn() { return orderMapper.toDTOs(orderService.findOwn());
    }

    @GetMapping("/teas")
    public List<OrderCountDTO> findTeas() { return orderService.findTeas();
    }

}
