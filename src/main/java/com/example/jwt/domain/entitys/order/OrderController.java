package com.example.jwt.domain.entitys.order;

import com.example.jwt.domain.entitys.order.dto.OrderCountDTO;
import com.example.jwt.domain.entitys.order.dto.OrderCreateDTO;
import com.example.jwt.domain.entitys.order.dto.OrderDTO;
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

    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll() {
        List<OrderDTO> orders = orderMapper.fromOrderToOrderDTO(orderService.findAll());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/page/{pageNo}/{pageSize}")
    public ResponseEntity<List<OrderCreateDTO>> getAllOrdersPage(@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        List<OrderCreateDTO> list = orderService.getAllOrdersPage(pageNo, pageSize);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/findown")
    public List<OrderCreateDTO> findOwn() { return orderMapper.toDTOs(orderService.findOwn());
    }

    @GetMapping("/teas")
    public List<OrderCountDTO> findTeas() { return orderService.findTeas();
    }

    // HIER SPEICHERST DU EINE NEUE BESTELLUNG
    @PostMapping
    public ResponseEntity<OrderCreateDTO> save(@RequestBody @Valid OrderCreateDTO orderCreateDTO) {
        Order order = orderService.createOrder(orderMapper.fromDTO(orderCreateDTO));
        return new ResponseEntity<>(orderMapper.toDTO(order), HttpStatus.CREATED);
    }

    //update für tests
    @PutMapping("/{id}")
    public ResponseEntity<OrderCreateDTO> updateById(@PathVariable UUID id, @Validated @RequestBody OrderCreateDTO orderCreateDTO) {
        Order order = orderService.updateById(id, orderMapper.fromDTO(new OrderCreateDTO()));
        return new ResponseEntity<>(orderMapper.toDTO(order), HttpStatus.OK);
    }

}